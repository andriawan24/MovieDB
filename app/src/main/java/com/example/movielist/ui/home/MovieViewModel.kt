package com.example.movielist.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielist.db.model.*
import com.example.movielist.repository.MovieRepository
import com.example.movielist.ui.baseViewModel.BaseViewModel
import com.example.movielist.util.AppResult
import com.example.movielist.util.SingleLiveEvent
import kotlinx.coroutines.launch

class MovieViewModel constructor(private val repository: MovieRepository) : BaseViewModel() {
    val showLoading = ObservableBoolean()
    val populerMovieList = MutableLiveData<Movie>()
    val showError = SingleLiveEvent<String>()
    val topRatedMovieList = MutableLiveData<MovieTopRated>()
    val nowPlayingMovieList = MutableLiveData<MovieNowPlaying>()



    fun getMoviePopuler(apiKey: String, page: Int) {
        showLoading.set(true)
        viewModelScope.launch {
            val result =  repository.getMoviePopuler(apiKey, page)

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    populerMovieList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getMovieTopRated(apiKey: String, page: Int) {
        showLoading.set(true)
        viewModelScope.launch {
            val result =  repository.getMovieTopRated(apiKey, page)

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    topRatedMovieList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getMovieNowPlaying(apiKey: String, page: Int) {
        showLoading.set(true)
        viewModelScope.launch {
            val result =  repository.getMovieNowPlaying(apiKey, page)

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    nowPlayingMovieList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }


}