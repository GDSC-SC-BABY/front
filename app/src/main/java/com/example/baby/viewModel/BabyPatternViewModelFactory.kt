package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyPatternRepository
import com.example.baby.network.BabyRepository


class BabyPatternViewModelFactory(private val babyPatternRepository: BabyPatternRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabyPatternViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BabyPatternViewModel(babyPatternRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}