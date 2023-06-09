package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class VideoLink (

    @SerializedName("id"  ) var id  : Int?    = null,
    @SerializedName("url" ) var url : String? = null

)