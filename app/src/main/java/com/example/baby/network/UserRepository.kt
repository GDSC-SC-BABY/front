
package com.example.baby.network


import com.example.baby.data.User
import retrofit2.Response

class UserRepository {

    suspend fun registerUser(user: User): Response<User> {
        return RetrofitClient.service.registerUser(user)
    }
}