package com.example.newsapp.data.repositoryimp

import com.example.newsapp.data.remote.Api
import com.example.newsapp.domain.model.NewsResponse
import com.example.newsapp.domain.repository.Repositroy
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val api:Api
) : Repositroy {
    override suspend fun getNews(country: String, appApi: String): NewsResponse {
        return api.getNews(country,appApi)
    }
}