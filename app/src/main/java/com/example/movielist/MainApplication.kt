package com.example.movielist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.example.movielist.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private val TAG = MainApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            ))
        }
    }

}