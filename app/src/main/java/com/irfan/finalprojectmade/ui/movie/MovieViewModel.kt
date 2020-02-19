package com.irfan.finalprojectmade.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.repository.MovieRepository

class MovieViewModel : ViewModel() {

    val repository = MovieRepository()
    var apiListener: ApiListener? = null
    fun fetchData() = repository.connectAPI(apiListener)
    fun getData(): MutableLiveData<List<Movie>> = repository.resultData
}