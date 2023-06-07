package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName
import java.util.Date


data class Player (

  @SerializedName("id"             ) var id             : Int?                      = null,
  @SerializedName("surname"        ) var surname        : String?                   = null,
  @SerializedName("name"           ) var name           : String?                   = null,
  @SerializedName("patronymic"     ) var patronymic     : String?                   = null,
  @SerializedName("birthDate"      ) var birthDate      : Date?                   = null,
  @SerializedName("parents"        ) var parents        : ArrayList<Parents>        = arrayListOf(),
  @SerializedName("sex"            ) var sex            : String?                   = null,
  @SerializedName("number"         ) var number         : Int?                      = null,
  @SerializedName("teamYear"       ) var teamYear       : Int?                      = null,
  @SerializedName("photo"          ) var photo          : String?                   = null,
  @SerializedName("position"       ) var position       : Position?                 = Position(),
  @SerializedName("physiologyList" ) var physiologyList : ArrayList<PhysiologyList> = arrayListOf(),
  @SerializedName("stats"          ) var stats          : ArrayList<Stats>          = arrayListOf()

)