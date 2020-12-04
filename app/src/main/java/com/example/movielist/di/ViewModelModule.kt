package com.example.movielist.di

import com.example.movielist.ui.detailMovie.DetailMovieViewModel
import com.example.movielist.ui.home.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        MovieViewModel(get())
    }

    viewModel {
        DetailMovieViewModel(get())
    }
}