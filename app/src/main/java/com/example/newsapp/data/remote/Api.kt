package com.example.newsapp.data.remote

import com.example.newsapp.domain.model.NewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country") country:String, @Query("apiKey"
        ) apiKey:String):NewsResponse

}