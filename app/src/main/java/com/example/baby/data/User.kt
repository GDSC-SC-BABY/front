package com.example.baby.data

import com.google.gson.annotations.SerializedName

data class User(
    val nickname: String,
    val residence: String,
    val relation: String
)

data class UserResponse(
    @SerializedName("name") val name: String,
    @SerializedName("residence") val residence: String
)

