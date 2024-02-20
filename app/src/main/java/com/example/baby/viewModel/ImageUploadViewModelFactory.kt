package com.example.baby.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyFoodRepository
import com.example.baby.network.ImageRepository
import com.example.baby.network.UserRepository

class ImageUploadViewModelFactory(private val application: Application, private val imageRepository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageUploadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageUploadViewModel(application, imageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}