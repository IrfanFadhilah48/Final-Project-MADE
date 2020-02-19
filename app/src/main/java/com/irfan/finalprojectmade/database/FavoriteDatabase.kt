package com.irfan.finalprojectmade.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.irfan.finalprojectmade.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase(){

    abstract fun favoriteDAO(): FavoriteDAO

    companion object{
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase{

//                synchronized(this){
//                    var instance = INSTANCE
//                    if (INSTANCE == null){
//                        instance = Room.databaseBuilder(context.applicationContext,
//                            FavoriteDatabase::class.java, "FavoriteCatalog.db")
//                            .build()
//
//                    INSTANCE = instance
//                }
//            }
//            return INSTANCE
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "FavoriteCatalog.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}