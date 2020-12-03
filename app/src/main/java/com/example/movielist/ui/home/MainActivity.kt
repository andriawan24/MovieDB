package com.example.movielist.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.movielist.BuildConfig
import com.example.movielist.R
import com.example.movielist.databinding.ActivityMainBinding
import com.example.movielist.db.model.Movie
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel by viewModel<MovieViewModel>()
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this

        movieViewModel.populerMovieList.observe(this, Observer {
            if (it.isNotEmpty() && it != null) {
                Log.d("apaya", it.get(0).results.get(0).title)
            }
        } )

      movieViewModel.getMoviePopuler(BuildConfig.API_KEY, 1)

    }
}