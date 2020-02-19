@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.irfan.finalprojectmade.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.irfan.finalprojectmade.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun displayImageOriginal(context: Context, imageView: ImageView, url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .apply(
            RequestOptions()
                .error(R.drawable.no_preview)
                .placeholder(R.drawable.ic_launcher_background)
        )
        .into(imageView)
}

@SuppressLint("SimpleDateFormat")
fun parseDate(tanggal: String): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val parseFormat = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
    var formattedDate: String? = null
    try {
        val convertedDate = originalFormat.parse(tanggal)
        formattedDate = parseFormat.format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
//    var tanggalFormat: String? = null
//    try{
//        val date = originalFormat.parse(tanggal)
//        tanggalFormat = parseFormat.format(date)
//    }
//    catch (e: ParseException){
//        e.printStackTrace()
//    }
//
//    return tanggalFormat.toString()
}

fun convertLanguage(language: String): String {
    return when (language) {
        "US" -> "en"
        else -> language
    }
}
