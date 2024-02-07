package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

// Sleep 조회 api는 sleepId가 필요한데 db에 있는 슬립 아이디를 앱에서 어떻게 알지?

data class SleepPattern(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("endTime") val endTime: LocalDateTime,
    @SerializedName("memo") val memo: String?,
    @SerializedName("babyId") val babyId: Int
)

data class MedicinePattern(
    val startTime: String,
    val medicineType: String,
    val memo: String,
    val babyId: Int
)

data class DefecationPattern(
    val startTime: String,
    val defecationStatus: String,
    val memo: String,
    val babyId: Int
)

data class BathPattern(
    val startTime: String,
    val endTime: String,
    val memo: String,
    val babyId: Int
)

data class PatternResponse(
    val state: Boolean
)