
package com.example.baby.network


import com.example.baby.data.*
import retrofit2.Response
import java.time.LocalDateTime

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

    suspend fun getSnacksByDate(babyId: Int, date: String):Response<SnackAllResponse>{
        return RetrofitClient.service.getSnacksByDate(babyId = babyId, date = date)
    }
}