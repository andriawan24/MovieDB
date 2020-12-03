package com.example.movielist.repository


import aish.android.countries.db.MoviesPopulerDao
import android.content.Context
import android.util.Log
import com.example.movielist.db.model.Movie
import com.example.movielist.db.model.ResultMovie
import com.example.movielist.network.ApiService
import com.example.movielist.util.AppResult
import com.example.movielist.util.NetworkManager.isOnline
import com.example.movielist.util.TAG
import com.example.movielist.util.Utils.handleApiError
import com.example.movielist.util.Utils.handleSuccess
import com.example.movielist.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviePopulerRepositoryImp(private val apiService: ApiService
                                ,
                                private val context: Context,
                                private val dao: MoviesPopulerDao) : MoviePopulerRepository {

    override suspend fun getMoviePopuler(apiKey: String, page: Int): AppResult<Movie> {
        val response = apiService.getMoviePopuler(apiKey, page)

        return try {
                if (response.isSuccessful) {
                    //save the data
                    response.body()?.let {
                        withContext(Dispatchers.IO) { dao.add(it.results) }
                    }
                    handleSuccess(response)

                } else {
                    if(isOnline(context)){
                        handleApiError(response)
                    }else{
                        context.noNetworkConnectivityError()
                    }
                }
            } catch (e: Exception) {
            Log.e("kenapaya", e.toString())

            AppResult.Error(e)
        }




    }


}