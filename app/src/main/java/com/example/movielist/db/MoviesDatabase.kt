package com.example.movielist.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.db.model.*

@Database(
    entities = [ResultMovie::class, ResultMovieTopRated::class, ResultMovieNowPlaying::class, FavoriteModel::class],
    version = 3, exportSchema = false
)


abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviePopulerDao: MoviesPopulerDao
}