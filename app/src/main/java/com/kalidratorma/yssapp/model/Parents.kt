package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Parents (

  @SerializedName("id"          ) var id          : Int?    = null,
  @SerializedName("surname"     ) var surname     : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("patronymic"  ) var patronymic  : String? = null,
  @SerializedName("phoneNumber" ) var phoneNumber : String? = null,
  @SerializedName("email"       ) var email       : String? = null,
  @SerializedName("sex"         ) var sex         : String? = null,
  @SerializedName("login"       ) var login       : String? = null,
  @SerializedName("password"    ) var password    : String? = null

)