package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName
import com.kalidratorma.yssapp.model.security.user.User


data class Coach (

    @SerializedName("id"          ) var id          : Int?      = null,
    @SerializedName("surname"     ) var surname     : String?   = null,
    @SerializedName("name"        ) var name        : String?   = null,
    @SerializedName("patronymic"  ) var patronymic  : String?   = null,
    @SerializedName("phoneNumber" ) var phoneNumber : String?   = null,
    @SerializedName("sex"         ) var sex         : String?   = null,
    @SerializedName("education"   ) var education   : String?   = null,
    @SerializedName("contract"    ) var contract    : Contract? = Contract(),
    @SerializedName("coachType"   ) var coachType   : String?   = null,
    @SerializedName("user"        ) var user        : User?     = User()

)