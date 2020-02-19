package com.irfan.finalprojectmade.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfan.finalprojectmade.database.FavoriteDAO
import com.irfan.finalprojectmade.model.Favorite
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class FavoriteRepository(private val favoriteDAO: FavoriteDAO){

    var listLiveData: LiveData<List<Favorite>> = favoriteDAO.getAll()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var checkData = MutableLiveData<Int>()

    fun insertData(favorite: Favorite){
        val disposable = Observable.fromCallable{favoriteDAO.insertData(favorite)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun getData(): LiveData<List<Favorite>>{
        return listLiveData
    }

    fun deleteAllData(favorite: Favorite){
        val disposable = Observable.fromCallable { favoriteDAO.deleteAllData(favorite) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun deleteData(id: Long){
        val disposable = Observable.fromCallable { favoriteDAO.deleteData(id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun getCheckData(id: Long): MutableLiveData<Int> {
        val disposable = Observable.fromCallable { favoriteDAO.getCountFavorite(id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Int>{
                override fun accept(t: Int?) {
                    Log.e("datanya", t.toString())
                    checkData.value = t
                }
            }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {

                }
            })
        compositeDisposable.add(disposable)
        return checkData
    }
}