package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Task (

    @SerializedName("id"         ) var id         : Int,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("description") var description: String,
    @SerializedName("minutes"    ) var minutes    : Int?                  = null,
    @SerializedName("qty"        ) var qty        : Int?                  = null,
    @SerializedName("videoLinks" ) var videoLinks : ArrayList<ContentFile> = arrayListOf(),
    @SerializedName("photoLinks" ) var photoLinks : ArrayList<ContentFile> = arrayListOf(),
    @SerializedName("schedule"   ) var schedule   : ArrayList<Schedule>   = arrayListOf(),
    @SerializedName("players"    ) var players    : ArrayList<Player>    = arrayListOf(),
    @SerializedName("coach"      ) var coach      : Coach?                = Coach()

)