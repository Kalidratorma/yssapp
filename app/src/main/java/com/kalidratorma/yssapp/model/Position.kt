package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Position (

  @SerializedName("id"        ) var id        : Int?    = null,
  @SerializedName("shortName" ) var shortName : String? = null,
  @SerializedName("name"      ) var name      : String? = null

)