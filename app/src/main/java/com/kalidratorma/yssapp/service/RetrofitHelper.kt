package com.kalidratorma.yssapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    private const val baseUrl = "http://kalidratorma.com:8000/api/"
    var httpClient: OkHttpClient = OkHttpClient.Builder().build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}