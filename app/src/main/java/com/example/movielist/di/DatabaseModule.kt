package com.example.movielist.di


import aish.android.countries.db.MoviesPopulerDao
import android.app.Application
import androidx.room.Room
import com.example.movielist.db.MoviesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): MoviesDatabase {
       return Room.databaseBuilder(application, MoviesDatabase::class.java, "movies")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: MoviesDatabase): MoviesPopulerDao {
        return  database.moviePopulerDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }


}
