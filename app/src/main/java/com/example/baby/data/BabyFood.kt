package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class BabyFood(
    @SerializedName("babyId") val babyId: Int,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("amount") val amount: Int,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("basePorridgeName") val baseMeal: String,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)

data class Topping(
    @SerializedName("name") val name: String,
    @SerializedName("amount") val amount: Int
)

data class BabyFoodAllResponse(
    @SerializedName("babyFoodGetResList") val babyFoodGetResList: List<BabyFoodInfo>
)

data class BabyFoodInfo(
    @SerializedName("babyFoodId") val babyFoodId: Int,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("basePorridgeName") val baseMeal: String

)


data class BabyFoodResponse(
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("basePorridge") val baseMeal: String,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)

