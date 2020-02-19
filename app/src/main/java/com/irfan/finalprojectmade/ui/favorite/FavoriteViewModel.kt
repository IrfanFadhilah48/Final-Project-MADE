package com.irfan.finalprojectmade.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfan.finalprojectmade.database.FavoriteDatabase
import com.irfan.finalprojectmade.model.Favorite
import com.irfan.finalprojectmade.repository.FavoriteRepository


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteRepository: FavoriteRepository

    init {
        val favoriteDAO = FavoriteDatabase.getInstance(application).favoriteDAO()
        favoriteRepository = FavoriteRepository(favoriteDAO)
    }

    fun insert(favorite: Favorite) {
        favoriteRepository.insertData(favorite)
    }

    fun deleteAll(favorite: Favorite){
        favoriteRepository.deleteAllData(favorite)
    }

    fun deleteData(id: Long){
        favoriteRepository.deleteData(id)
    }

    fun checkData(id: Long) = favoriteRepository.getCheckData(id)
    fun getCheckData(): MutableLiveData<Int> = favoriteRepository.checkData


    fun fetchData() = favoriteRepository.getData()
    fun getData(): LiveData<List<Favorite>> = favoriteRepository.listLiveData

}