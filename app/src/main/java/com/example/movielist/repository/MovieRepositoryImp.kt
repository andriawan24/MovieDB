package com.example.movielist.repository


import android.content.Context
import android.util.Log
import com.example.movielist.R
import com.example.movielist.db.MoviesPopulerDao
import com.example.movielist.db.model.*
import com.example.movielist.network.ApiService
import com.example.movielist.util.AppResult
import com.example.movielist.util.NetworkManager.isOnline
import com.example.movielist.util.Utils.handleApiError
import com.example.movielist.util.Utils.handleSuccess
import com.example.movielist.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImp(private val apiService: ApiService,
                         private val context: Context,
                         private val dao: MoviesPopulerDao
) : MovieRepository {

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

            AppResult.Error(e)
        }




    }

    override suspend fun getMovieTopRated(apiKey: String, page: Int): AppResult<MovieTopRated> {
        val response = apiService.getMovieTopRated(apiKey, page)

        return try {
            if (response.isSuccessful) {
                //save the data
                response.body()?.let {
                    withContext(Dispatchers.IO) { dao.addTopRatedMovie(it.results) }
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

    override suspend fun getMovieNowPlaying(apiKey: String, page: Int): AppResult<MovieNowPlaying> {
        val response = apiService.getMovieNowPlaying(apiKey, page)

        return try {
            if (response.isSuccessful) {
                //save the data
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        it.results.let {
                            dao.addMovieNowPlaying(it)

                        }
                    }
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

    override suspend fun getMovieReview(
        url: String,
        apiKey: String,
        page: Int
    ): AppResult<MovieReview>? {
        val response = apiService.getMovieReview(url, apiKey, page)

        return try {
            if (response.isSuccessful) {
                //save the data
                if(response.body()?.results?.size == 0){
                    return AppResult.Error(Exception("Data Kosong"))
                }else {
                    handleSuccess(response)
                }
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