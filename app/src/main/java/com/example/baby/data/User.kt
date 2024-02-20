package com.example.baby.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String
)

data class UserResponse(
    @SerializedName("name") val name: String
)

data class UserDuplicateResponse(
    @SerializedName("state") val state: Boolean
)

data class BabyCode(
    @SerializedName("userId") val userId: String,
    @SerializedName("babyCode") val babyCode: String,
)
