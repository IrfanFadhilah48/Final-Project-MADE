package com.irfan.finalprojectmade.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy
import com.irfan.finalprojectmade.model.Favorite

@Dao
interface FavoriteDAO{

    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorites")
    fun getAllWidget(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(favorite: Favorite)

    @Delete
    fun deleteAllData(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id=:id")
    fun deleteData(id: Long)

    @Query("SELECT COUNT(id) FROM favorites WHERE id=:id")
    fun getCountFavorite(id:Long) :Int

    @Query("SELECT * FROM favorites")
    fun getAllContent(): Cursor
//    fun getCountFavorite(id:Long): LiveData<Int>
}