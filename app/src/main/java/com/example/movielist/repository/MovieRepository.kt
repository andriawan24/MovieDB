package com.example.movielist.repository

import com.example.movielist.db.model.*
import com.example.movielist.util.AppResult

interface MovieRepository {

    suspend fun getMoviePopuler(apiKey: String, page: Int): AppResult<Movie>

    suspend fun getMovieTopRated(apiKey: String, page: Int): AppResult<MovieTopRated>

    suspend fun getMovieNowPlaying(apiKey: String, page: Int): AppResult<MovieNowPlaying>


    suspend fun getMovieReview(url: String, apiKey: String, page: Int ): AppResult<MovieReview>?

}