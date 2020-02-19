package com.irfan.finalprojectmade.network

import com.irfan.finalprojectmade.model.ResponseMovie
import com.irfan.finalprojectmade.model.ResponseSerialtv
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("discover/movie")
    fun getMovie(@Query("language") language: String): Observable<ResponseMovie>

    @GET("discover/tv")
    fun getTv(@Query("language") language: String): Observable<ResponseSerialtv>

    @GET("search/tv")
    fun searchTv(@Query("language") language: String, @Query("query") query: String): Observable<ResponseSerialtv>

    @GET("search/movie")
    fun searchMovie(@Query("language") language: String, @Query("query") query: String): Observable<ResponseMovie>

    @GET("discover/movie")
    fun getMovieRelease(@Query("primary_release_date.gte") dategte: String, @Query("primary_release_date.lte") datelte: String): Observable<ResponseMovie>

    @GET("discover/movie")
    fun getMovieReleases(@Query("api_key") apikey: String, @Query("primary_release_date.gte") dategte: String, @Query("primary_release_date.lte") datelte: String): Observable<ResponseMovie>
}