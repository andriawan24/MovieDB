package com.example.movielist.di


import android.content.Context
import com.example.movielist.db.MoviesPopulerDao
import com.example.movielist.network.ApiService
import com.example.movielist.repository.MovieRepository
import com.example.movielist.repository.MovieRepositoryImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideMovieRepository(api: ApiService, context: Context, dao : MoviesPopulerDao): MovieRepository {
        return MovieRepositoryImp(api, context, dao)
    }
    single { provideMovieRepository(get(), androidContext(), get()) }

}