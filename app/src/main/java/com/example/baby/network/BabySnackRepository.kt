
package com.example.baby.network


import com.example.baby.data.BabyFood
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyFoodResponse
import com.example.baby.data.Snack
import com.example.baby.data.SnackAllResponse
import com.example.baby.data.SnackResponse
import com.example.baby.data.User
import com.example.baby.data.UserDuplicateResponse
import com.example.baby.data.UserResponse
import retrofit2.Response

class BabySnackRepository {

    suspend fun registerBabySnack(babySnack: Snack): Response<UserDuplicateResponse> {
        return RetrofitClient.service.registerBabySnack(babySnack)
    }

    suspend fun getAllSnackByBabyId(babyId: Int): Response<SnackAllResponse> {
        return RetrofitClient.service.getAllSnackByBabyId(babyId)
    }

    suspend fun getSnackInfoByBabyFoodId(snackId: Int):Response<SnackResponse>{
        return RetrofitClient.service.getSnackDetailBySnackId(snackId = snackId)
    }
}