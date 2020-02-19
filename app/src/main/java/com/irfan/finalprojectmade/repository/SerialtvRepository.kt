package com.irfan.finalprojectmade.repository

import android.annotation.SuppressLint
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

class SerialtvRepository{

    var apiInterface: ApiInterface = ApiNetwork.connect()
    var resultData = MutableLiveData<List<Serialtv>>()
    private lateinit var compositeDisposable: CompositeDisposable

    @SuppressLint("DefaultLocale")
    fun getData(apiListener: ApiListener?){
        compositeDisposable = CompositeDisposable()
        val language = convertLanguage(Locale.getDefault().country).toLowerCase()
        val disposable = apiInterface.getTv(language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<ResponseSerialtv>{
                override fun accept(responseSerialtv: ResponseSerialtv?) {
                    resultData.value = responseSerialtv?.result
                    apiListener?.onSucess()
                }
            }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {
                    apiListener?.onError(t?.message)
                }
            })
        compositeDisposable.add(disposable)
    }
}