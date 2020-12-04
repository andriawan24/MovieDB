package com.example.movielist.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class MovieReview(
    @SerializedName("page") val page : Int?,
    @SerializedName("results") val results : List<ResultMovieReview>?,
    @SerializedName("total_pages") val total_pages : Int?,
    @SerializedName("total_results") val total_results : Int?
)


data class ResultMovieReview(
    @SerializedName("author") val author : String?,
    @SerializedName("content") val content : String?,
    @SerializedName("created_at") val created_at : String?,
    @SerializedName("id") val id : String?,
    @SerializedName("updated_at") val updated_at : String?,
    @SerializedName("url") val url : String?
)
//
//@Parcelize
//data class AuthorDetails (
//
//    @SerializedName("name") val name : String,
//    @SerializedName("username") val username : String,
//    @SerializedName("avatar_path") val avatar_path : String,
//    @SerializedName("rating") val rating : Int
//): Parcelable
