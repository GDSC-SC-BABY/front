package com.example.baby.network

import com.example.baby.data.PatternResponse
import com.example.baby.data.SleepPattern
import com.example.baby.data.User
import com.example.baby.data.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("user")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("user/{userId}")
    suspend fun checkUserId(@Path("userId") userId: String): Response<UserResponse>

    @POST("sleep")
    suspend fun registerSleepPattern(@Body sleepPattern: SleepPattern):Response<PatternResponse>
}