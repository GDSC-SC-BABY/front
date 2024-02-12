
package com.example.baby.network


import com.example.baby.data.User
import com.example.baby.data.UserResponse
import retrofit2.Response

class UserRepository {

    suspend fun registerUser(user: User): Response<User> {
        return RetrofitClient.service.registerUser(user)
    }
    suspend fun getUserInfo(userId: String):Response<UserResponse>{
        return RetrofitClient.service.getUserInfo(userId = userId)
    }
}