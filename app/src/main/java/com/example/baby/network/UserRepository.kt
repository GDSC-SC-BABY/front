
package com.example.baby.network


import com.example.baby.data.User
import com.example.baby.data.UserResponse
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository {

    suspend fun registerUser(user: User): Response<User> {
        return RetrofitClient.service.registerUser(user)
    }
    suspend fun checkUserId(userId: String):UserResponse{
        return RetrofitClient.service.checkUserId(userId = userId)
    }
}