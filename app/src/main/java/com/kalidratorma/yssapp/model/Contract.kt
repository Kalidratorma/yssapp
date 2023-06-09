package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName


data class Contract (

    @SerializedName("id"                       ) var id                       : Int?    = null,
    @SerializedName("contractNumber"           ) var contractNumber           : String? = null,
    @SerializedName("contractorContractNumber" ) var contractorContractNumber : String? = null,
    @SerializedName("contractSubject"          ) var contractSubject          : String? = null,
    @SerializedName("contractType"             ) var contractType             : String? = null,
    @SerializedName("expDate"                  ) var expDate                  : String? = null,
    @SerializedName("paymentTerms"             ) var paymentTerms             : String? = null

)