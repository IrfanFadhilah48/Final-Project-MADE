package com.irfan.finalprojectmade.network

interface ApiListener {
    fun onSucess()
    fun onError(message: String?)
}