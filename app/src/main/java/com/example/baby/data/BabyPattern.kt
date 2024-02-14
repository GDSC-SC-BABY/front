package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

abstract class BabyPattern


data class SleepPattern(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("endTime") val endTime: LocalDateTime,
    @SerializedName("memo") val memo: String?,
    @SerializedName("babyId") val babyId: Int
): BabyPattern()

data class SleepDetails(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("endTime") val endTime: LocalDateTime,
    @SerializedName("memo") val memo: String?,
)

data class MedicinePattern(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("medicineType")val medicineType: String?,
    @SerializedName("memo") val memo: String?,
    @SerializedName("babyId") val babyId: Int
): BabyPattern()

data class MedicineDetails(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("medicineType") val medicineType: String?,
    @SerializedName("memo") val memo: String?,
)

data class DefecationPattern(
    val startTime: String,
    val defecationStatus: String?,
    val memo: String?,
    val babyId: Int
): BabyPattern()

data class BathPattern(
    val startTime: String,
    val endTime: String,
    val memo: String?,
    val babyId: Int
): BabyPattern()

data class PatternResponse(
    val state: Boolean
)