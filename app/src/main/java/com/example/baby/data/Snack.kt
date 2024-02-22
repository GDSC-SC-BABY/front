package com.example.baby.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime


data class Snack(
    @SerializedName("babyId") val babyId: Int,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("amount") val amount: Int,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)

data class SnackAllResponse(
    @SerializedName("snackGetResList") val snackGetResList: List<SnackInfo>
)

data class SnackResponse(
    @SerializedName("dateTime") val dateTime: LocalDate,
    @SerializedName("amount") val amount: Int,
    @SerializedName("imageUrl") val url: String,
    @SerializedName("specialNote") val note: String,
    @SerializedName("toppingList") val toppingList: List<Topping>,
)


data class SnackInfo(
    @SerializedName("snackId") val snackId: Int,
    @SerializedName("dateTime") val dateTime: LocalDateTime,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("amount") val amount: Int
)

data class Beverage(
    @SerializedName("name") val name: String,
    @SerializedName("hasAllergy") val hasAllergy: Boolean
)

//data class Topping(
//    @SerializedName("name") val name: String,
//    @SerializedName("hasAllergy") val hasAllergy: Boolean
//)