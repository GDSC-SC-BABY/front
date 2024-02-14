package com.example.baby.network

import android.app.Activity
import com.example.baby.data.Baby
import com.example.baby.data.BabyResponse
import retrofit2.Response

class BabyRepository {
    suspend fun registerBaby(baby: Baby): Response<BabyResponse> {
        return RetrofitClient.service.registerBaby(baby)
    }
}
