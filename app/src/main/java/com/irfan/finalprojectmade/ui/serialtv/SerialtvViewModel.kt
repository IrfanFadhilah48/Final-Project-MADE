package com.irfan.finalprojectmade.ui.serialtv

import androidx.lifecycle.ViewModel
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.repository.SerialtvRepository

class SerialtvViewModel : ViewModel() {

    val repository = SerialtvRepository()
    var apiListener: ApiListener? = null
    fun getData() = repository.getData(apiListener)
    fun fetchData() = repository.resultData
}