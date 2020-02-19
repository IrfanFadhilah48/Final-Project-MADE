package com.irfan.favoriteapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id: Long = 0,
    var title: String?,
    var voteAverage: Float,
    var posterPath: String?,
    var overview: String?,
    var releaseDate: String?,
    var popularity: String?,
    var type: String?
): Parcelable{
    companion object{
        const val TABLE_NAME = "favorites"
        const val _ID = "id"
        const val TITLE = "title"
        const val VOTE = "vote"
        const val IMAGEURL = "imageUrl"
        const val DESCRIPTION = "description"
        const val RELEASE = "release"
        const val POPULARITY = "popularity"
        const val TYPE = "type"
    }
}
