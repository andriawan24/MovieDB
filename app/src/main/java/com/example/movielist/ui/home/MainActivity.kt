package com.example.movielist.ui.home

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movielist.BuildConfig
import com.example.movielist.R
import com.example.movielist.databinding.ActivityMainBinding
import com.example.movielist.db.MoviesDatabase
import com.example.movielist.db.MoviesPopulerDao
import com.example.movielist.db.model.Movie
import com.example.movielist.db.model.ResultMovie
import com.example.movielist.db.model.ResultMovieNowPlaying
import com.example.movielist.db.model.ResultMovieTopRated
import com.example.movielist.ui.favorite.FavoriteActivity
import com.example.movielist.ui.home.adapter.NowPlayingAdapter
import com.example.movielist.ui.home.adapter.PopularAdapter
import com.example.movielist.ui.home.adapter.TopRatedAdapter
import com.example.movielist.util.EndlessRecyclerOnScrollListener
import com.example.movielist.util.NetworkManager.isOnline
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val movieViewModel by viewModel<MovieViewModel>()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var db : MoviesDatabase

    private var dataMoviePopuler : ArrayList<ResultMovie> = ArrayList()
    private var dataMovieTopRated : ArrayList<ResultMovieTopRated> = ArrayList()
    private  var dataMovieNowPlaying: ArrayList<ResultMovieNowPlaying> = ArrayList()

    private var pagePopular = 1
    private var pageTopRated = 1
    private var pageNowPlaying = 1

    private lateinit var  popularAdapter: PopularAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListenerPopular: EndlessRecyclerOnScrollListener


    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var linearlayoutManagerTopRated: LinearLayoutManager
    private lateinit var scrollListenerTopRated: EndlessRecyclerOnScrollListener

    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var linearlayoutManagerNowPlaying: LinearLayoutManager
    private lateinit var scrollListenerNowPlaying: EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this

        db = Room.databaseBuilder(
                this,
        MoviesDatabase::class.java, "movies"
        ).allowMainThreadQueries().build()

        initAll()



        movieViewModel.populerMovieList.observe(this, Observer {
            if ( it != null) {
                popularAdapter.dataPopular.addAll(it.results)
                popularAdapter.notifyDataSetChanged()
            }
        } )

        movieViewModel.topRatedMovieList.observe(this, Observer {
            if (it != null){
                topRatedAdapter.dataTopRated.addAll(it.results)
                topRatedAdapter.notifyDataSetChanged()
            }
        })

        movieViewModel.nowPlayingMovieList.observe(this, Observer {
            if (it != null){

                it.results?.let { it1 -> nowPlayingAdapter.dataNowPlaying.addAll(it1) }
                nowPlayingAdapter.notifyDataSetChanged()
            }
        })


        if (isOnline(this)) {
            popularAdapter.dataPopular.clear()
            topRatedAdapter.dataTopRated.clear()
            nowPlayingAdapter.dataNowPlaying.clear()

            movieViewModel.getMoviePopuler(BuildConfig.API_KEY, pagePopular)
            movieViewModel.getMovieTopRated(BuildConfig.API_KEY, pageTopRated)
             movieViewModel.getMovieNowPlaying(BuildConfig.API_KEY, pageNowPlaying)
        }else{
            popularAdapter.dataPopular.clear()
            topRatedAdapter.dataTopRated.clear()
            nowPlayingAdapter.dataNowPlaying.clear()

            Toast.makeText(this, "Tidak Ada Koneksi Internet, Data diambil dari cache", Toast.LENGTH_LONG).show()
            nowPlayingAdapter.dataNowPlaying.addAll(db.moviePopulerDao.findAllMovieNowPlaying())
            nowPlayingAdapter.notifyDataSetChanged()

            topRatedAdapter.dataTopRated.addAll(db.moviePopulerDao.findAllTopRatedMovie())
            topRatedAdapter.notifyDataSetChanged()

            popularAdapter.dataPopular.addAll(db.moviePopulerDao.findAll())
            popularAdapter.notifyDataSetChanged()
        }



        ivFavorite.setOnClickListener(View.OnClickListener {
            startActivity(Intent(MainActivity@this, FavoriteActivity::class.java))
        })
    }

    private fun initAll() {
        popularAdapter = PopularAdapter(this, dataMoviePopuler)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_popularMovie.layoutManager = linearLayoutManager
        rv_popularMovie.adapter = popularAdapter
        scrollListenerPopular = object : EndlessRecyclerOnScrollListener(linearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
              pagePopular+=1

                movieViewModel.showLoading.set(true)
                movieViewModel.getMoviePopuler(BuildConfig.API_KEY, pagePopular)

            }

        }
        rv_popularMovie.addOnScrollListener(scrollListenerPopular)

        topRatedAdapter = TopRatedAdapter(this, dataMovieTopRated)
        linearlayoutManagerTopRated = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_topratedMovie.layoutManager = linearlayoutManagerTopRated
        rv_topratedMovie.adapter = topRatedAdapter
        scrollListenerTopRated = object  : EndlessRecyclerOnScrollListener(linearlayoutManagerTopRated){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                pageTopRated+=1
                movieViewModel.showLoading.set(true)
                movieViewModel.getMovieTopRated(BuildConfig.API_KEY, pageTopRated)
            }

        }
        rv_topratedMovie.addOnScrollListener(scrollListenerTopRated)

        nowPlayingAdapter = NowPlayingAdapter(this, dataMovieNowPlaying)
        linearlayoutManagerNowPlaying = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_nowplayingMovie.layoutManager = linearlayoutManagerNowPlaying
        rv_nowplayingMovie.adapter = nowPlayingAdapter
        scrollListenerNowPlaying = object : EndlessRecyclerOnScrollListener(linearlayoutManagerNowPlaying){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                pageNowPlaying +=1
                movieViewModel.showLoading.set(true)
                movieViewModel.getMovieNowPlaying(BuildConfig.API_KEY, pageNowPlaying)
            }
        }
        rv_nowplayingMovie.addOnScrollListener(scrollListenerNowPlaying)
    }

}