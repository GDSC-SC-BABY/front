package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.BabyPatternRepository


class BabyPatternRecordViewModelFactory(private val babyPatternRepository: BabyPatternRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabyPatternRecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BabyPatternRecordViewModel(babyPatternRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}