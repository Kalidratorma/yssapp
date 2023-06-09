package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class PhotoLink (

    @SerializedName("id"  ) var id  : Int?    = null,
    @SerializedName("url" ) var url : String? = null

)