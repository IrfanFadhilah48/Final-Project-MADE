package com.irfan.finalprojectmade.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites")
data class Favorite(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "vote") var voteAverage: Float,
    @ColumnInfo(name = "imageUrl") var posterPath: String?,
    @ColumnInfo(name = "description") var overview: String?,
    @ColumnInfo(name = "release") var releaseDate: String?,
    @ColumnInfo(name = "popularity") var popularity: String?,
    @ColumnInfo(name = "type") var type: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: Long = 0
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
