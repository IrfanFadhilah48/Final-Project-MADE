package com.irfan.finalprojectmade.ui.search.serialtv

import android.util.Log
import androidx.lifecycle.ViewModel
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.repository.SearchSerialtvRepository

class SerialtvSearchViewModel : ViewModel() {

    private val repository = SearchSerialtvRepository()
    var apiListener: ApiListener? = null
    fun getData(query: String){
        Log.e("datanyaViewModel", query)
        repository.getData(apiListener, query)
    }
    fun fetchData() = repository.resultData
}
