package com.example.movielist.network

import com.example.movielist.db.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/week")
    suspend fun getMoviePopuler(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<Movie>
}