package com.example.baby.data

import com.google.gson.annotations.SerializedName


data class BabyFood(
    @SerializedName("babyId") val babyId: String,
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("basePorridgeList") val basePorridgeList: List<BasePorridge>,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)

data class BabyFoodAllResponse(
    @SerializedName("babyFoodGetResList") val babyFoodGetResList: List<BabyFoodInfo>
)

data class BabyFoodResponse(
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("basePorridgeList") val basePorridgeList: List<BasePorridge>,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)


data class BabyFoodInfo(
    @SerializedName("babyFoodId") val babyFoodId: Int,
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("amount") val amount: Int
)

data class BasePorridge(
    @SerializedName("name") val name: String,
    @SerializedName("hasAllergy") val hasAllergy: Boolean
)

data class Topping(
    @SerializedName("name") val name: String,
    @SerializedName("hasAllergy") val hasAllergy: Boolean
)