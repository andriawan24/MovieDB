package com.example.movielist.db


import aish.android.countries.db.MoviesPopulerDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.movielist.db.model.Movie
import com.example.movielist.db.model.ResultMovie

@Database(
    entities = [ResultMovie::class],
    version = 1, exportSchema = false
)


abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviePopulerDao: MoviesPopulerDao
}