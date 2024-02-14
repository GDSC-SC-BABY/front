package com.example.baby.network

import com.example.baby.data.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // User
    @POST("user")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("user/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): Response<UserResponse>


    // Sleep Pattern
    @POST("sleep")
    suspend fun registerSleepPattern(@Body sleepPattern: SleepPattern):Response<PatternResponse>

    @GET("sleep/{sleepId}")
    suspend fun getSleepById(@Path("sleepId") sleepId: Long): Response<SleepDetails>

    @DELETE("sleep/{sleepId}")
    suspend fun deleteSleep(@Path("sleepId") sleepId: String): Response<PatternResponse>
    @PATCH("sleep/{sleepId}")
    suspend fun updateSleep(
        @Path("sleepId") sleepId: Long,
        @Body sleepUpdate: SleepDetails
    ): Response<PatternResponse>
}