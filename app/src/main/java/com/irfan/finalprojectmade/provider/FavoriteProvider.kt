package com.irfan.finalprojectmade.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.irfan.finalprojectmade.database.FavoriteDAO
import com.irfan.finalprojectmade.database.FavoriteDatabase
import com.irfan.finalprojectmade.model.Favorite
import java.lang.IllegalArgumentException

class FavoriteProvider : ContentProvider() {

    companion object{
        private const val CODE_FAVORITE_DIR = 1
        private const val CODE_FAVORITE_ITEM = 2

        private const val AUTHORITY = "com.irfan.finalprojectmade.provider"

//        private val URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + Favorite.TABLE_NAME)
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    }
    private lateinit var favoriteDAO: FavoriteDAO

    init {
        uriMatcher.addURI(AUTHORITY, Favorite.TABLE_NAME, CODE_FAVORITE_DIR)
        uriMatcher.addURI(AUTHORITY, Favorite.TABLE_NAME + "/#", CODE_FAVORITE_ITEM)
    }

    override fun onCreate(): Boolean {
//        favoriteDAO = FavoriteDatabase.getInstance(context!!).favoriteDAO()
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        when(uriMatcher.match(uri)){
            CODE_FAVORITE_DIR -> return "vnd.android.cursor.dir/" + AUTHORITY + "." + Favorite.TABLE_NAME
            CODE_FAVORITE_ITEM -> return "vnd.android.cursor.item/" + AUTHORITY + "." + Favorite.TABLE_NAME
            else -> throw IllegalArgumentException("Unknown Uri Type" +uri)
        }
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val code  = uriMatcher.match(uri)
        if (code == CODE_FAVORITE_DIR || code == CODE_FAVORITE_ITEM){
            if (context == null){
                Log.e("Movie Provider","Context null")
            }
            var cursor:Cursor? = null
            favoriteDAO = FavoriteDatabase.getInstance(context!!).favoriteDAO()
            if (code == CODE_FAVORITE_DIR){
                cursor = favoriteDAO.getAllContent()
            }else{

            }

            cursor?.setNotificationUri(context?.contentResolver,uri)
            return cursor
        }else{
            throw IllegalArgumentException("Unknow Uris : "+uri)
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
