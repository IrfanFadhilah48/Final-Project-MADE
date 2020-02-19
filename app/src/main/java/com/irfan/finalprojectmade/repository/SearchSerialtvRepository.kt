package com.irfan.finalprojectmade.repository

import androidx.lifecycle.MutableLiveData
import com.irfan.finalprojectmade.model.ResponseSerialtv
import com.irfan.finalprojectmade.model.Serialtv
import com.irfan.finalprojectmade.network.ApiInterface
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.network.ApiNetwork
import com.irfan.finalprojectmade.util.convertLanguage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchSerialtvRepository {

    private lateinit var compositeDisposable: CompositeDisposable
    var resultData = MutableLiveData<List<Serialtv>>()
    private val apiInterface: ApiInterface = ApiNetwork.connect()

    fun getData(apiListener: ApiListener?, query: String){
        compositeDisposable = CompositeDisposable()
        val language = convertLanguage(Locale.getDefault().country).toLowerCase()
        val disposable = apiInterface.searchTv(language, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<ResponseSerialtv>{
                override fun accept(responseSerialtv: ResponseSerialtv?) {
                    apiListener?.onSucess()
                    resultData.value = responseSerialtv?.result
                }
            }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {
                    apiListener?.onError(t?.message)
                }
            })
        compositeDisposable.add(disposable)
    }
}