package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Baby(
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("birthHeight") val birthHeight: Int,
    @SerializedName("birthWeight") val birthWeight: Int
)

data class BabyResponse(
    val state: Boolean
)