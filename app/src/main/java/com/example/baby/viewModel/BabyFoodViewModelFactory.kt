package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyFoodRepository
import com.example.baby.network.UserRepository

class BabyFoodViewModelFactory(private val babyFoodRepository: BabyFoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabyFoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BabyFoodViewModel(babyFoodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}