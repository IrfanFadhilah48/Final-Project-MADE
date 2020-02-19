package com.irfan.finalprojectmade.network

import android.content.Context

interface NotificationCallback {
    fun onSucess(context: Context?)
    fun onError(message: String?)
}