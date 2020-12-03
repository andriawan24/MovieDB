package com.example.movielist.di


import aish.android.countries.db.MoviesPopulerDao
import android.content.Context
import com.example.movielist.network.ApiService
import com.example.movielist.repository.MoviePopulerRepository
import com.example.movielist.repository.MoviePopulerRepositoryImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideMovieRepository(api: ApiService, context: Context, dao : MoviesPopulerDao): MoviePopulerRepository {
        return MoviePopulerRepositoryImp(api, context, dao)
    }
    single { provideMovieRepository(get(), androidContext(), get()) }

}