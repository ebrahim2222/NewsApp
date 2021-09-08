package com.example.newsapp.di

import com.example.newsapp.data.remote.Api
import com.example.newsapp.data.repositoryimp.RepositoryImp
import com.example.newsapp.ui.newshome.NewsAdapter
import com.example.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Modules {
    @Provides
    @Singleton
    fun  provideRetrofit(): Retrofit {

        val logingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
        logingInterceptor.level  = HttpLoggingInterceptor.Level.BODY

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logingInterceptor)
            .build()

        val builder: Retrofit.Builder = Retrofit.Builder()
        val retrofit: Retrofit = builder.baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit):Api{
        val api:Api = retrofit.create(Api::class.java)
        return api
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(api:Api):RepositoryImp{
        return RepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideNewsAdapter():NewsAdapter{
        return NewsAdapter()
    }
}