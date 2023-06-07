package com.kalidratorma.yssapp.service

import com.kalidratorma.yssapp.model.Player
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/player/{id}")
    suspend fun getPlayerById(@Path("id") id:Long): Response<Player>

}