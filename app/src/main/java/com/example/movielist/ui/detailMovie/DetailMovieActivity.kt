package com.example.movielist.ui.detailMovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movielist.BuildConfig
import com.example.movielist.R
import com.example.movielist.databinding.ActivityDetailMovieBinding
import com.example.movielist.databinding.ActivityFavoriteBinding
import com.example.movielist.db.MoviesDatabase
import com.example.movielist.db.model.FavoriteModel
import com.example.movielist.ui.home.MainActivity
import com.example.movielist.ui.home.MovieViewModel
import com.example.movielist.util.NetworkManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    private val detalMovieViewModel by viewModel<DetailMovieViewModel>()
    private lateinit var activityMainBinding: ActivityDetailMovieBinding
    private var url = ""
    private var page = 1
    private var idMovie:String? = ""
    private var reviews:String? = ""
    private lateinit var db : MoviesDatabase
    private var favoriteData: ArrayList<FavoriteModel>? = ArrayList()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_detail_movie)

        activityMainBinding.lifecycleOwner = this


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        db = Room.databaseBuilder(
            this,
            MoviesDatabase::class.java, "movies"
        ).allowMainThreadQueries().build()

        favoriteData?.add(db.moviePopulerDao.findByIdFavorite(intent.getStringExtra("idMovie")))

        if(favoriteData.toString() !="[null]") {
            Log.d("wewew", favoriteData.toString())
            ivFavoriteDetail.setImageResource(R.drawable.heartfullblack)

            }else{
            ivFavoriteDetail.setImageResource(R.drawable.heart)
            favoriteData?.clear()
        }

        intent.getStringExtra("nameMovie").let {
            tvTitleDetail.text = it
        }

        intent.getStringExtra("releaseMovie").let {
            tvReleaseDetail.text = it
        }

        intent.getStringExtra("describeMovie").let {
            tvDescriptionDetail.text = it
        }

        intent.getStringExtra("imageMovie").let {
                    Glide.with(DetailMovieActivity@this)
                        .load(BuildConfig.BASE_URL_IMAGE + it)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_250)
                        .into(ivDetailImage)
        }

        intent.getStringExtra("idMovie").let {
            idMovie = it



        }
        url = BuildConfig.BASE_URL + "movie/"+idMovie + "/reviews"

        detalMovieViewModel.movieReviewList.observe(this, Observer {
            it.results?.let { result->
                reviews = result[0].content
                tvReviewDetail.text = result[0].content
            }
        })
        if (NetworkManager.isOnline(this)) {
            detalMovieViewModel.getMovieReview(url, BuildConfig.API_KEY, page)
        }else{
            Toast.makeText(this, "Tidak Ada Koneksi Internet", Toast.LENGTH_LONG).show()
        }
        ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        ivFavoriteDetail.setOnClickListener(View.OnClickListener {

            var favoriteSave: List<FavoriteModel> = listOf(
                FavoriteModel(intent.getStringExtra("idMovie")?.toInt(), intent.getStringExtra("nameMovie"),
                    intent.getStringExtra("describeMovie"), intent.getStringExtra("imageMovie"),intent.getStringExtra("releaseMovie"))
            )
            if(favoriteData!!.size > 0) {

                    ivFavoriteDetail.setImageResource(R.drawable.heart)
                    db.moviePopulerDao.deleteFavorite(favoriteSave[0])
                    favoriteData?.clear()
                Toast.makeText(DetailMovieActivity@this, "Deleted", Toast.LENGTH_LONG).show()

            }else{
                ivFavoriteDetail.setImageResource(R.drawable.heartfullblack)
                db.moviePopulerDao.addFavoriteMovie(favoriteSave)
                Toast.makeText(DetailMovieActivity@this, "Saved", Toast.LENGTH_LONG).show()
                favoriteData?.add(db.moviePopulerDao.findByIdFavorite(intent.getStringExtra("idMovie")))

            }


        })

        ivShare.setOnClickListener(View.OnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        })
    }
}