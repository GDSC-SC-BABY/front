package com.example.baby.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException

class ImageRepository(private val context: Context) {

    @SuppressLint("Recycle")
    suspend fun uploadImage(imageUri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val requestBody = inputStream?.let { stream ->
            val bytes = stream.readBytes()
            RequestBody.create("image/jpeg".toMediaTypeOrNull(), bytes)
        }
        val body = requestBody?.let {
            MultipartBody.Part.createFormData("image", "filename.jpg", it)
        }

        if (body != null) {
            val response = RetrofitClient.service.uploadImage(body)
            return response.imageUrl
        } else {
            throw FileNotFoundException("File not found for the provided Uri")
        }
    }
}