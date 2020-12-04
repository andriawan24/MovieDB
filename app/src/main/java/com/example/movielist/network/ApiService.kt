package com.example.movielist.network

import com.example.movielist.db.model.Movie
import com.example.movielist.db.model.MovieNowPlaying
import com.example.movielist.db.model.MovieReview
import com.example.movielist.db.model.MovieTopRated
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url




interface ApiService {



    @GET("movie/popular")
    suspend fun getMoviePopuler(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<Movie>

    @GET("movie/top_rated")
    suspend fun getMovieTopRated(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieTopRated>

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieNowPlaying>

    @GET
    suspend fun getMovieReview(@Url url: String,@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieReview>
}