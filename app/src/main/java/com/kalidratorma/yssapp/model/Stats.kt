package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Stats (

  @SerializedName("id"      ) var id      : Int?    = null,
  @SerializedName("date"    ) var date    : String? = null,
  @SerializedName("goal"    ) var goal    : Int?    = null,
  @SerializedName("assist"  ) var assist  : Int?    = null,
  @SerializedName("points"  ) var points  : Int?    = null,
  @SerializedName("penalty" ) var penalty : Int?    = null

)