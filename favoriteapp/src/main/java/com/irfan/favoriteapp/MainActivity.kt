package com.irfan.favoriteapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.room.RoomMasterTable.TABLE_NAME
import com.irfan.favoriteapp.adapter.FavoriteAdapter
import com.irfan.favoriteapp.model.Favorite
import com.irfan.favoriteapp.model.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.toast
import java.lang.Exception
import java.lang.IllegalArgumentException


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var adapter: FavoriteAdapter
    private var datas = arrayListOf<Favorite>()
    private val AUTHORITY = "com.irfan.finalprojectmade.provider"
    private val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(Favorite.TABLE_NAME)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter(this)
        adapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener{
            override fun onItemClicked(favorite: Favorite) {
                toast(favorite.title.toString())
            }
        })
        rv_favorite.layoutManager = layoutManager
        supportLoaderManager.initLoader(1, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        startShimmer()
        when(id){
            1 -> return CursorLoader(applicationContext, CONTENT_URI, null, null, null, null)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        try {
            datas = MappingHelper.mapCursorToArrayList(data!!)
            adapter.setDataFavorite(datas)
            rv_favorite.adapter = adapter
            stopShimmer()
        }
        catch (e: Exception){
            Log.e("error", e.printStackTrace().toString())
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private fun startShimmer() {
        rv_favorite.visibility = View.INVISIBLE
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
        rv_favorite.visibility = View.VISIBLE
    }

}
