package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Task (

    @SerializedName("id"         ) var id         : Int?                  = null,
    @SerializedName("name"       ) var name       : String?               = null,
    @SerializedName("minutes"    ) var minutes    : Int?                  = null,
    @SerializedName("qty"        ) var qty        : Int?                  = null,
    @SerializedName("videoLinks" ) var videoLinks : ArrayList<VideoLink> = arrayListOf(),
    @SerializedName("photoLinks" ) var photoLinks : ArrayList<PhotoLink> = arrayListOf(),
    @SerializedName("schedule"   ) var schedule   : ArrayList<Schedule>   = arrayListOf(),
    @SerializedName("players"    ) var players    : ArrayList<Player>    = arrayListOf(),
    @SerializedName("coach"      ) var coach      : Coach?                = Coach()

)