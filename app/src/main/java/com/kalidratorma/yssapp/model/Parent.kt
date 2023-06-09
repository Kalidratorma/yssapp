package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName
import com.kalidratorma.yssapp.model.security.user.User


data class Parent (
  @SerializedName("id"          ) var id          : Int,
  @SerializedName("surname"     ) var surname     : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("patronymic"  ) var patronymic  : String? = null,
  @SerializedName("phoneNumber" ) var phoneNumber : String? = null,
  @SerializedName("sex"         ) var sex         : String? = null,
  @SerializedName("contracts"    ) var contracts    : List<Contract>? = null,
  @SerializedName("user"        ) var user        : User?     = User()
)