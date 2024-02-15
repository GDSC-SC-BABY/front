package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Activity(
    @SerializedName("activityId") val activityId: Long,
    @SerializedName("activityType") val activityType: String,
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("endTime") val endTime: LocalDateTime,
    @SerializedName("specificStatus") val specificStatus: String,
    @SerializedName("memo") val memo: String
)