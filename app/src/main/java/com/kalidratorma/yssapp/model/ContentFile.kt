package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName

data class ContentFile (
    @SerializedName("id") var id: Long? = null,
    @SerializedName("name") var name: String? = null
)