package com.example.movielist.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.movielist.R
import com.example.movielist.databinding.ActivityFavoriteBinding
import com.example.movielist.databinding.ActivityMainBinding
import com.example.movielist.db.MoviesDatabase
import com.example.movielist.db.model.FavoriteModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private var favoriteModel : ArrayList<FavoriteModel> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db : MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_favorite)
        activityMainBinding.lifecycleOwner = this
        db = Room.databaseBuilder(
            this,
            MoviesDatabase::class.java, "movies"
        ).allowMainThreadQueries().build()

        favoriteModel.addAll(db.moviePopulerDao.findAllFavoriteMovie())
        adapter = FavoriteAdapter(this, favoriteModel)
        linearLayoutManager = LinearLayoutManager(this)
        rv_favorite.layoutManager = linearLayoutManager
        rv_favorite.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        favoriteModel.clear()
        favoriteModel.addAll(db.moviePopulerDao.findAllFavoriteMovie())
        adapter.notifyDataSetChanged()
    }
}