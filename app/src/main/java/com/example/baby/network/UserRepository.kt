
package com.example.baby.network


import com.example.baby.data.BabyCode
import com.example.baby.data.User
import com.example.baby.data.UserDuplicateResponse
import com.example.baby.data.UserResponse
import retrofit2.Response

class UserRepository {

    suspend fun registerUser(user: User): Response<UserDuplicateResponse> {
        return RetrofitClient.service.registerUser(user)
    }
    suspend fun getUserInfo(userId: String):Response<UserResponse>{
        return RetrofitClient.service.getUserInfo(userId = userId)
    }
    suspend fun checkDuplicateUserId(userId: String): Response<UserDuplicateResponse> {
        return RetrofitClient.service.checkDuplicateUserId(userId = userId)
    }

    suspend fun addBabyCode(babyCode: BabyCode): Response<UserDuplicateResponse>{
        return RetrofitClient.service.addBabyCode(babyCode)
    }
}