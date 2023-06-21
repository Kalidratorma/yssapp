package com.kalidratorma.yssapp.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class TaskReport(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("reportDate") val reportDate: Date? = null,
    @SerializedName("task") val task: Task? = null,
    @SerializedName("player") val player: Player? = null,
    @SerializedName("taskDate") val taskDate: Date? = null,
    @SerializedName("report") val report: String? = null,
    @SerializedName("photoLinks") val photoLinks: List<ContentFile>? = null,
    @SerializedName("videoLinks") val videoLinks: List<ContentFile>? = null
)