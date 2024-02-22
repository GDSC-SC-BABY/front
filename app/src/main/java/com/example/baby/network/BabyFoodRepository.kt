
package com.example.baby.network


import com.example.baby.data.*
import retrofit2.Response
import java.time.LocalDateTime

class BabyFoodRepository {

    suspend fun registerBabyFood(babyFood: BabyFood): Response<UserDuplicateResponse> {
        return RetrofitClient.service.registerBabyFood(babyFood)
    }

    suspend fun getAllBabyFoodByBabyId(babyId: Int): Response<BabyFoodAllResponse> {
        return RetrofitClient.service.getAllBabyFoodByBabyId(babyId)
    }

    suspend fun getBabyFoodInfoByBabyFoodId(babyFoodId: Int):Response<BabyFoodResponse>{
        return RetrofitClient.service.getBabyFoodDetailByBabyFoodId(babyFoodId = babyFoodId)
    }
    suspend fun getBabyFoodsByDate(babyId: Int, date: String):Response<BabyFoodInfo>{
        return RetrofitClient.service.getBabyFoodByDate(babyId = babyId, date = date)
    }
}