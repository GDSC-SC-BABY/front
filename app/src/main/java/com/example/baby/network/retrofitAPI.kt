package com.example.baby.network

import com.example.baby.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user")
    suspend fun registerUser(@Body user: User): Response<User>
}