package com.example.movielist.repository

import com.example.movielist.db.model.Movie
import com.example.movielist.util.AppResult

interface MoviePopulerRepository {

    suspend fun getMoviePopuler(apiKey: String, page: Int): AppResult<Movie>
}