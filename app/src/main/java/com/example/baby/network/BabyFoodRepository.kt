
package com.example.baby.network


import com.example.baby.data.BabyFood
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyFoodResponse
import com.example.baby.data.User
import com.example.baby.data.UserDuplicateResponse
import com.example.baby.data.UserResponse
import retrofit2.Response

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
}