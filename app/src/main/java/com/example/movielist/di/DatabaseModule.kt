package com.example.movielist.di


import android.app.Application
import androidx.room.Room
import com.example.movielist.db.MoviesDatabase
import com.example.movielist.db.MoviesPopulerDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): MoviesDatabase {
       return Room.databaseBuilder(application, MoviesDatabase::class.java, "movies")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideMoviesDao(database: MoviesDatabase): MoviesPopulerDao {
        return  database.moviePopulerDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideMoviesDao(get()) }


}
