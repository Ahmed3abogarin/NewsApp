package com.ahmed.newsapp.data.remote

import com.ahmed.newsapp.data.remote.dto.NewsResponse
import com.ahmed.newsapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY

    ): NewsResponse


    @GET("everything")
    suspend fun newsSearch(
        @Query("q") query:String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}