package com.irfan.finalprojectmade.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseMovie(
    @SerializedName("results") val result: ArrayList<Movie>
)

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("popularity")
    val popularity: String
): Parcelable