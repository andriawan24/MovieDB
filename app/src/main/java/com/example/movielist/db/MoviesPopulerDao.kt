package com.example.movielist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movielist.db.model.*

@Dao
interface MoviesPopulerDao {

    @Query("SELECT * FROM MoviePopuler")
    fun findAll(): List<ResultMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: List<ResultMovie>)

    @Query("SELECT * FROM MovieTopRated")
    fun findAllTopRatedMovie(): List<ResultMovieTopRated>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTopRatedMovie(movie: List<ResultMovieTopRated>)

    @Query("SELECT * FROM MovieNowPlaying")
    fun findAllMovieNowPlaying(): List<ResultMovieNowPlaying>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieNowPlaying(movie: List<ResultMovieNowPlaying>?)

    @Query("SELECT * FROM FavoriteMovie")
    fun findAllFavoriteMovie(): List<FavoriteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteMovie(movie: List<FavoriteModel>?)

    @Query("SELECT * FROM FavoriteMovie WHERE idMovie=:id LIMIT 1")
    fun findByIdFavorite(id : String?): FavoriteModel

    @Delete
    fun deleteFavorite(model: FavoriteModel)
}