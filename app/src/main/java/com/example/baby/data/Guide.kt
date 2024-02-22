package com.example.baby.data

import com.google.gson.annotations.SerializedName

data class Guide(
    @SerializedName("title") val title: String,
    @SerializedName("age") val age: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("pdfUrl") val pdfUrl: String
)