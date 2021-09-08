package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.NewsResponse

interface Repositroy {

    suspend fun getNews(country:String, appApi:String):NewsResponse
}