package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName
import java.util.Date


data class Player (

    @SerializedName("id"             ) var id             : Int,
    @SerializedName("surname"        ) var surname        : String,
    @SerializedName("name"           ) var name           : String,
    @SerializedName("patronymic"     ) var patronymic     : String?                   = null,
    @SerializedName("birthDate"      ) var birthDate      : Date,
    @SerializedName("parents"        ) var parents        : ArrayList<Parent>        = arrayListOf(),
    @SerializedName("sex"            ) var sex            : String,
    @SerializedName("number"         ) var number         : Int?                      = null,
    @SerializedName("teamYear"       ) var teamYear       : Int?                      = null,
    @SerializedName("photo"          ) var photo          : String?                   = null,
    @SerializedName("position"       ) var position       : Position?                 = Position(),
    @SerializedName("physiologyList" ) var physiologyList : ArrayList<PhysiologyList> = arrayListOf(),
    @SerializedName("stats"          ) var stats          : ArrayList<Stats>          = arrayListOf()

)