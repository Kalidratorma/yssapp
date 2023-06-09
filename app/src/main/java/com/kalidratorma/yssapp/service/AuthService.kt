package com.kalidratorma.yssapp.service

import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.model.security.auth.AuthenticationRequest
import com.kalidratorma.yssapp.model.security.auth.AuthenticationResponse
import com.kalidratorma.yssapp.model.security.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("/api/auth/register")
    suspend fun register(@Body body: User): Response<AuthenticationResponse>

    @POST("/api/auth/enter")
    suspend fun enter(@Body body: AuthenticationRequest): Response<AuthenticationResponse>
}