package com.irfan.finalprojectmade.network

import android.util.Log
import com.irfan.finalprojectmade.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiNetwork {

    private fun loggingInterceptor(): HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        return interceptor
    }

    private fun okHttpClient(): OkHttpClient{
//        var requestParams: Request? = null
//        val okHttpClients = OkHttpClient()
//        okHttpClients.interceptors().add(object : Interceptor{
//            override fun intercept(chain: Interceptor.Chain): Response {
//                var requestParam = chain.request()
//                val url = requestParam.url()
//                    .newBuilder()
//                    .addQueryParameter("api_key", BuildConfig.API_KEY)
//                    .addQueryParameter("language", BuildConfig.LANGUAGE)
//                    .build()
//                requestParams = requestParam.newBuilder().url(url).build()
//                return chain.proceed(requestParams)
//            }
//        })

        var requestParams: Request
        val interceptor = Interceptor { chain ->
            val requestParam = chain.request()
            val url = requestParam.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
//                .addQueryParameter("language", BuildConfig.LANGUAGE)
                .build()
            requestParams = requestParam.newBuilder().url(url).build()
            chain.proceed(requestParams)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

        return okHttpClient
    }

    private fun retrofit(): Retrofit{
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient())
            .build()

        return retrofit
    }

    fun connect(): ApiInterface = retrofit().create(ApiInterface::class.java)
}