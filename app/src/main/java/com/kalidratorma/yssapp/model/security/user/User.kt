package com.kalidratorma.yssapp.model.security.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("username") var username: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("role") var role: Role? = null
)