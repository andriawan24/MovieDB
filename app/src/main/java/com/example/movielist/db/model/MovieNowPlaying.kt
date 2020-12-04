package com.example.movielist.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieNowPlaying(
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<ResultMovieNowPlaying>,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("total_results") val total_results : Int

): Parcelable

@Entity(tableName = "MovieNowPlaying")
@Parcelize
data class ResultMovieNowPlaying(
    @PrimaryKey(autoGenerate = true) val iddb: Int,
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("id") val id : Int,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_title") val original_title : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("title") val title : String
): Parcelable

