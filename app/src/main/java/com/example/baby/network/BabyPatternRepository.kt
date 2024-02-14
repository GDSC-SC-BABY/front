package com.example.baby.network

import com.example.baby.data.PatternResponse
import com.example.baby.data.SleepPattern
import com.example.baby.data.User
import com.example.baby.data.UserResponse
import retrofit2.Response

class BabyPatternRepository {

    suspend fun registerSleepPattern(sleepPattern: SleepPattern): Response<PatternResponse> {
        return RetrofitClient.service.registerSleepPattern(sleepPattern)
    }
    suspend fun getUserInfo(userId: String): Response<UserResponse> {
        return RetrofitClient.service.getUserInfo(userId = userId)
    }
}