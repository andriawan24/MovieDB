package com.example.movielist.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "FavoriteMovie")
@Parcelize
data class FavoriteModel(
    @PrimaryKey val idMovie: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val releaseMovie:String? = null
): Parcelable