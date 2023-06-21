package com.kalidratorma.yssapp.service

import com.kalidratorma.yssapp.model.security.auth.AuthenticationRequest
import com.kalidratorma.yssapp.model.security.auth.AuthenticationResponse
import com.kalidratorma.yssapp.model.security.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(@Body body: User): Response<AuthenticationResponse>

    @POST("auth/enter")
    suspend fun enter(@Body body: AuthenticationRequest): Response<AuthenticationResponse>
}