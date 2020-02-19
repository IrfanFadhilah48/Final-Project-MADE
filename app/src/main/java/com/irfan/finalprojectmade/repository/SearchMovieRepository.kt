package com.irfan.finalprojectmade.repository

import androidx.lifecycle.MutableLiveData
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.model.ResponseMovie
import com.irfan.finalprojectmade.network.ApiInterface
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.network.ApiNetwork
import com.irfan.finalprojectmade.util.convertLanguage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchMovieRepository {

    var apiInterface: ApiInterface = ApiNetwork.connect()
    var resultData = MutableLiveData<List<Movie>>()
    private lateinit var compositeDisposable: CompositeDisposable

    fun getData(apiListener: ApiListener?, query: String){
        compositeDisposable = CompositeDisposable()

        val language = convertLanguage(Locale.getDefault().country).toLowerCase()
        val disposable = apiInterface.searchMovie(language, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<ResponseMovie>{
                override fun accept(responseMovie: ResponseMovie?) {
                    apiListener?.onSucess()
                    resultData.value = responseMovie?.result
                }
            }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {
                    apiListener?.onError(t?.message)
                }

            })
        compositeDisposable.add(disposable)
    }

}