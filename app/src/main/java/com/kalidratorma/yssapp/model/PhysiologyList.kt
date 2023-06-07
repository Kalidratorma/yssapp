package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class PhysiologyList (

  @SerializedName("id"     ) var id     : Int?    = null,
  @SerializedName("date"   ) var date   : String? = null,
  @SerializedName("height" ) var height : Int?    = null,
  @SerializedName("weight" ) var weight : Int?    = null,
  @SerializedName("grip"   ) var grip   : String? = null

)