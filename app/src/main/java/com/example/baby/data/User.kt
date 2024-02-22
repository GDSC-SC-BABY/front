package com.example.baby.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("relation") val relation: String,
    @SerializedName("imageUri") val imageUri: String
)

data class UserResponse(
    @SerializedName("name") val name: String,
    @SerializedName("relation") val relation: String
)

data class UserDuplicateResponse(
    @SerializedName("state") val state: Boolean
)

data class BabyCode(
    @SerializedName("userId") val userId: String,
    @SerializedName("babyCode") val babyCode: String,
)

data class CoParentResponse(
    val coParentResponse: List<CoParents>
)

data class CoParents(
    @SerializedName("name") val name: String,
    @SerializedName("relation") val relation: String,
    @SerializedName("imageUri") val userImage: String
)
