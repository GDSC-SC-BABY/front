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
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("memo") val memo: String?,
    @SerializedName("babyId") val babyId: Int
): BabyPattern()

data class BathPattern(
    @SerializedName("startTime") val startTime: LocalDateTime,
    @SerializedName("endTime") val endTime: LocalDateTime,
    @SerializedName("memo") val memo: String?,
    @SerializedName("babyId") val babyId: Int
): BabyPattern()

data class PatternResponse(
    val state: Boolean
)