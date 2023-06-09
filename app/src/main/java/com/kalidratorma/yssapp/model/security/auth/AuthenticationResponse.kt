package com.kalidratorma.yssapp.model.security.auth

import com.google.gson.annotations.SerializedName
import com.kalidratorma.yssapp.model.security.user.Role
import java.util.Date

data class AuthenticationResponse(
    @SerializedName("token") var token: String? = null,
    @SerializedName("role") var role: Role? = null,
    @SerializedName("exp") var exp: Date? = null
)