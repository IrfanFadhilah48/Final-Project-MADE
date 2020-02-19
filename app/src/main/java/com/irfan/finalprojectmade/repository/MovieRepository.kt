package com.irfan.finalprojectmade.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.irfan.finalprojectmade.BuildConfig
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.model.ResponseMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import com.irfan.finalprojectmade.network.ApiInterface
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.network.ApiNetwork
import com.irfan.finalprojectmade.network.NotificationCallback
import com.irfan.finalprojectmade.util.convertLanguage
import kotlin.collections.ArrayList


class MovieRepository{

    var apiInterface: ApiInterface = ApiNetwork.connect()
    var resultData = MutableLiveData<List<Movie>>()
    var releaseData = ArrayList<Movie>()
    lateinit var compositeDisposable: CompositeDisposable

    @SuppressLint("DefaultLocale")
    fun connectAPI(apiListener: ApiListener?){
        compositeDisposable = CompositeDisposable()
        val language = convertLanguage(Locale.getDefault().country).toLowerCase()
        val disposable = apiInterface.getMovie(language)
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

    fun getRelease(gte:String, lte:String, notificationCallback: NotificationCallback?, context: Context){
        compositeDisposable = CompositeDisposable()
        val disposable = apiInterface.getMovieReleases(BuildConfig.API_KEY, gte, lte)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<ResponseMovie>{
                override fun accept(responseMovie: ResponseMovie) {
                    Log.e("datanyaRepository", responseMovie.result.toString())
                    releaseData.addAll(responseMovie.result)
                    notificationCallback?.onSucess(context)
                    Log.e("datanyaAfterRelease", releaseData.toString())
                }
            }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {
                    notificationCallback?.onError(t?.message)
                }
            })
        compositeDisposable.add(disposable)
    }
}