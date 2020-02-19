package com.irfan.finalprojectmade.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseSerialtv(
    @SerializedName("results") val result: ArrayList<Serialtv>
)

@Parcelize
data class Serialtv(
    @SerializedName("id")
    val id: String,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("popularity")
    val popularity: String?
): Parcelable