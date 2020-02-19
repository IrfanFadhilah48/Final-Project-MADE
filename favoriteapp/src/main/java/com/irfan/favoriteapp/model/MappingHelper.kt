package com.irfan.favoriteapp.model

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(dataCursor: Cursor): ArrayList<Favorite> {
        val favorite = arrayListOf<Favorite>()
        while (dataCursor.moveToNext()) {
            val id = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite._ID)).toLong()
            val title = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.TITLE))
            val vote = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.VOTE)).toFloat()
            val posterPath = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.IMAGEURL))
            val description = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.DESCRIPTION))
            val date = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.RELEASE))
            val popularity = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.POPULARITY))
            val type = dataCursor.getString(dataCursor.getColumnIndexOrThrow(Favorite.TYPE))
            favorite.add(Favorite(id, title, vote, posterPath, description, date, popularity, type))
        }
        return favorite
    }
}