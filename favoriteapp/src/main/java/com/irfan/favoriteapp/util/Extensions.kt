package com.irfan.favoriteapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.irfan.favoriteapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun displayImageOriginal(context: Context, imageView: ImageView, url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .apply(RequestOptions()
            .error(R.drawable.no_preview)
            .placeholder(R.drawable.ic_launcher_background))
        .into(imageView)
}

@SuppressLint("SimpleDateFormat")
fun parseDate(tanggal: String): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val parseFormat = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
    var tanggalFormat: String? = null
    try{
        val date = originalFormat.parse(tanggal)
        tanggalFormat = parseFormat.format(date!!)
    }
    catch (e: ParseException){
        e.printStackTrace()
    }

    return tanggalFormat
}

fun convertLanguage(language: String): String{
    return when(language){
        "US" -> "en"
        else -> language
    }
}
