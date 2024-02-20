package com.example.baby.network

import com.example.baby.data.*
import retrofit2.Response
import retrofit2.http.*
import java.util.regex.Pattern

interface ApiService {

    // User
    @POST("user")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("user")
    suspend fun getUserInfo(@Query("userId") userId: String): Response<UserResponse>

    // Baby
    @POST("baby")
    suspend fun registerBaby(@Body baby: Baby): Response<BabyResponse>

    // Sleep Pattern
    @POST("sleep")
    suspend fun registerSleepPattern(@Body sleepPattern: SleepPattern):Response<PatternResponse>

    @GET("sleep/{sleepId}")
    suspend fun getSleepById(@Path("sleepId") sleepId: Int): Response<SleepDetails>

    @DELETE("sleep/{sleepId}")
    suspend fun deleteSleep(@Path("sleepId") sleepId: Int): Response<PatternResponse>
    @PATCH("sleep/{sleepId}")
    suspend fun updateSleep(
        @Path("sleepId") sleepId: Int,
        @Body sleepUpdate: SleepDetails
    ): Response<PatternResponse>

    // Medicine Pattern
    @POST("medicine")
    suspend fun registerMedicinePattern(@Body medicinePattern: MedicinePattern): Response<PatternResponse>

    @GET("medicine/{medicineId}")
    suspend fun getMedicineById(@Path("medicineId") medicineId: Int): Response<MedicineDetails>

    @DELETE("medicine/{medicineId}")
    suspend fun deleteMedicine(@Path("medicineId") medicineId: Int): Response<PatternResponse>

    @PATCH("medicine/{medicineId}")
    suspend fun updateMedicine(
        @Path("medicineId") medicineId: Int,
        @Body medicineEdit: MedicineDetails
    ): Response<PatternResponse>

    @GET("activity/{babyId}")
    suspend fun getActivitiesByBabyIdAndDate(
        @Path("babyId") babyId: Int,
        @Query("date") date: String
    ): Response<List<Activity>>

    @POST("babyFood")
    suspend fun registerBabyFood(@Body babyFood: BabyFood): Response<BabyFood>

    @GET("babyFood")
    suspend fun getAllBabyFoodByBabyId(@Query("babyId") babyId: Int) : Response<BabyFoodAllResponse>

    @GET("babyFood/{babyFoodId}")
    suspend fun getBabyFoodDetailByBabyFoodId(@Path("babyFoodId") babyFoodId: Int): Response<BabyFoodResponse>

    @POST("snack")
    suspend fun registerSnack(@Body snack: BabyFood): Response<BabyFood>

    @GET("snack")
    suspend fun getAllSnackByBabyId(@Query("babyId") babyId: Int) : Response<BabyFoodAllResponse>

    @GET("snack/{snackId}")
    suspend fun getSnackDetailBySnackId(@Path("snackId") snackId: Int): Response<BabyFoodResponse>

}