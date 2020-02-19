package com.irfan.finalprojectmade.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.irfan.finalprojectmade.BuildConfig
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.database.FavoriteDAO
import com.irfan.finalprojectmade.database.FavoriteDatabase
import com.irfan.finalprojectmade.model.Favorite
import com.irfan.finalprojectmade.repository.FavoriteRepository
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

internal class StackRemoteViewFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {

    private var mWidgetItems: ArrayList<Favorite> = arrayListOf()
    private var repository: FavoriteRepository
    private var favoriteDAO: FavoriteDAO = FavoriteDatabase.getInstance(context).favoriteDAO()
    var bitmap :Bitmap? =null
    private var compositeDisposable = CompositeDisposable()

    init {
        repository = FavoriteRepository(favoriteDAO)
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onDataSetChanged() {
//        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.poster_aquaman))
//        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.poster_arrow))
//        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.poster_avengerinfinity))
//        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.poster_bohemian))
//        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.poster_bumblebee))
        mWidgetItems.addAll(favoriteDAO.getAllWidget())
//        val disposable = Observable.fromCallable {favoriteDAO.getAllWidget()}
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                val identityToken = Binder.clearCallingIdentity()
//                Log.e("datanya", it.toString())
//                mWidgetItems.addAll(it)
//                Binder.restoreCallingIdentity(identityToken)
//            }
//        compositeDisposable.add(disposable)
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        try {
            bitmap = Glide.with(context)
                .asBitmap()
                .load(BuildConfig.IMAGES + mWidgetItems[position].posterPath)
                .submit(256, 256)
                .get()
        }
        catch (e : Exception){

        }
        rv.setImageViewBitmap(R.id.imageView, bitmap)
        val extras = bundleOf(
            StackViewWidget.EXTRA_ITEM to position
        )
        val fillIntent = Intent()
        fillIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }


}