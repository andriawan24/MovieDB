package com.example.movielist.ui.detailMovie

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.db.model.MovieReview
import com.example.movielist.repository.MovieRepository
import com.example.movielist.ui.baseViewModel.BaseViewModel
import com.example.movielist.util.AppResult
import com.example.movielist.util.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailMovieViewModel constructor(private val repository: MovieRepository) : BaseViewModel() {
    val showLoading = ObservableBoolean()
    val movieReviewList = MutableLiveData<MovieReview>()
    val showError = SingleLiveEvent<String>()

    fun getMovieReview(url: String, apiKey: String, page: Int) {
        viewModelScope.launch {
            val result =  repository.getMovieReview(url, apiKey, page)

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    movieReviewList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }


    }
}