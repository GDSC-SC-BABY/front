package com.example.baby.network

import android.app.Activity
import com.example.baby.data.Baby
import com.example.baby.data.BabyIdResponse
import com.example.baby.data.BabyResponse
import com.example.baby.data.UserDuplicateResponse
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class BabyRepository {
    suspend fun registerBaby(baby: Baby): Response<BabyResponse> {
        return RetrofitClient.service.registerBaby(baby)
    }

    suspend fun checkDuplicateUserId(userId: String): BabyIdResponse {

        try {
            val response = RetrofitClient.service.getBabyIdByUserId(userId = userId)
            if (response.isSuccessful) {
                return response.body() ?: throw NullPointerException("Response body is null")
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            throw Exception("Error occurred: ${e.message}", e)
        }
    }
}
