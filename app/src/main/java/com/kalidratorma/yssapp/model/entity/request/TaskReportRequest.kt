package com.kalidratorma.yssapp.model.entity.request

import com.google.gson.annotations.SerializedName
import com.kalidratorma.yssapp.model.ContentFile

data class TaskReportRequest(
    @SerializedName("reportDate") val reportDate: Long,
    @SerializedName("taskId") val task: Int,
    @SerializedName("playerId") val player: Int,
    @SerializedName("taskDate") val taskDate: Long? = null,
    @SerializedName("report") val report: String,
    @SerializedName("photoLinks") val photoLinks: List<ContentFile>? = null,
    @SerializedName("videoLinks") val videoLinks: List<ContentFile>? = null
)