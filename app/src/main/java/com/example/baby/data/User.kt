package com.example.baby.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("babyId") val babyId: Int
)

data class UserResponse(
    @SerializedName("name") val name: String
)