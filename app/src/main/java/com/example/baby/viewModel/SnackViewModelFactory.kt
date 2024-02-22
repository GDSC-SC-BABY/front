package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyFoodRepository
import com.example.baby.network.BabySnackRepository
import com.example.baby.network.UserRepository

class SnackViewModelFactory(private val snackRepository: BabySnackRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabySnackRegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BabySnackRegisterViewModel(snackRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}