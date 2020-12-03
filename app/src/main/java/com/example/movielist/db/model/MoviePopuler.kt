package com.example.movielist.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<ResultMovie>,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("total_results") val total_results : Int
): Parcelable

@Entity(tableName = "MoviePopuler")
@Parcelize
data class ResultMovie(
    @PrimaryKey(autoGenerate = true) val iddb: Int,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("id") val id : Int,
    @SerializedName("overview") val overview : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_title") val original_title : String,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("title") val title : String,
    @SerializedName("video") val video : Boolean,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("media_type") val media_type : String
): Parcelable