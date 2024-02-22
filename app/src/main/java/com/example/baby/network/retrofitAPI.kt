package com.example.baby.network

import com.example.baby.data.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDateTime
import java.util.regex.Pattern

interface ApiService {

    // User
    @POST("user")
    suspend fun registerUser(@Body user: User): Response<UserDuplicateResponse>

    @GET("user")
    suspend fun getUserInfo(@Query("userId") userId: String): Response<UserResponse>

    @GET("/user/duplicate/{userId}")
    suspend fun checkDuplicateUserId(@Path("userId") userId: String): Response<UserDuplicateResponse>

    @PATCH("/user/addBaby")
    suspend fun addBabyCode(@Body babyCode: BabyCode): Response<UserDuplicateResponse>

    @GET("user/getBaby")
    suspend fun getBabyIdByUserId(@Query("userId") userId: String): Response<BabyIdResponse>

    // Baby
    @POST("baby")
    suspend fun registerBaby(@Body baby: Baby): Response<BabyResponse>

    @GET("baby/{babyId}")
    suspend fun getBabyInfoByBabyId(@Path("babyId") babyId: Int): Response<BabyInfo>

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

    // Defection
    @POST("/defecation")
    suspend fun registerDefecation(@Body request: DefecationPattern): Response<PatternResponse>

    //Bath
    @POST("/bath")
    suspend fun registerBath(@Body request: BathPattern): Response<PatternResponse>

    @POST("babyFood")
    suspend fun registerBabyFood(@Body babyFood: BabyFood): Response<UserDuplicateResponse>


    @POST("Snack")
    suspend fun registerBabySnack(@Body snack: Snack): Response<UserDuplicateResponse>

    @GET("babyFood")
    suspend fun getAllBabyFoodByBabyId(@Query("babyId") babyId: Int) : Response<BabyFoodAllResponse>

    @GET("babyFood/{babyFoodId}")
    suspend fun getBabyFoodDetailByBabyFoodId(@Path("babyFoodId") babyFoodId: Int): Response<BabyFoodResponse>

    @GET("Snack")
    suspend fun getAllSnackByBabyId(@Query("babyId") babyId: Int) : Response<SnackAllResponse>

    @GET("Snack/{snackId}")
    suspend fun getSnackDetailBySnackId(@Path("snackId") snackId: Int): Response<SnackResponse>

    @GET("snackList")
    suspend fun getSnacksByDate(
        @Query("babyId") babyId: Int,
        @Query("date") date: String
    ): Response<SnackInfo>

    @GET("babyFoodList")
    suspend fun getBabyFoodByDate(
        @Query("babyId") babyId: Int,
        @Query("date") date: String
    ): Response<BabyFoodInfo>

    @Multipart
    @POST("Image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<ResponseBody>

    @GET("guide")
    suspend fun getGuide(@Path("babyId") babyId: Int): Response<List<CoParents>>
}