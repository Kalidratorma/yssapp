package com.kalidratorma.yssapp.model.security.auth

import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)
