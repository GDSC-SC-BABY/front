package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class Baby(
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("birthHeight") val birthHeight: String,
    @SerializedName("birthWeight") val birthWeight: String,
    @SerializedName("userId") val userId: String
)

data class BabyInfo(
    @SerializedName("babyId") val babyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("birthHeight") val birthHeight: String,
    @SerializedName("birthWeight") val birthWeight: String,
    @SerializedName("babyCode") val babyCode: String
)



data class BabyResponse(
    @SerializedName("state") val state: Boolean
)

data class BabyIdResponse(
    @SerializedName("babyId") val babyId: String
)