package com.example.baby.network

import com.example.baby.data.BabyFood
import com.example.baby.data.Guide
import retrofit2.Response

class GuideRepository {
    suspend fun getGuide(): Response<List<Guide>> {
        return RetrofitClient.service.getGuide()
    }
}