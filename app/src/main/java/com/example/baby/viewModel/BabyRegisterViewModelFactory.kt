package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyRepository
import com.example.baby.network.UserRepository

class BabyRegisterViewModelFactory(private val babyRepository: BabyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabyRegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BabyRegisterViewModel(babyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}