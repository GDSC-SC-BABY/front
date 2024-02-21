package com.example.baby.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object RetrofitClient {
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime>,
            JsonDeserializer<LocalDateTime> {
            override fun serialize(
                src: LocalDateTime?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement {
                return JsonPrimitive(src?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            }

            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDateTime {
                return LocalDateTime.parse(json?.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }
        })
        .create()

    private const val BASE_URL = "http://34.64.184.71:8080/"

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}