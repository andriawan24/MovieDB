package com.example.movielist.di

import com.example.movielist.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideCountriesApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    single { provideCountriesApi(get()) }

}