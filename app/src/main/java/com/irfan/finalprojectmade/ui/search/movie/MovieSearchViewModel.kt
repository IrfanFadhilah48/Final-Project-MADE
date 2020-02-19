package com.irfan.finalprojectmade.ui.search.movie

import androidx.lifecycle.ViewModel
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.repository.SearchMovieRepository

class MovieSearchViewModel : ViewModel() {
    private val repository = SearchMovieRepository()
    var apiListener: ApiListener? = null
    fun getData(query: String) = repository.getData(apiListener, query)
    fun fetchData() = repository.resultData
}
