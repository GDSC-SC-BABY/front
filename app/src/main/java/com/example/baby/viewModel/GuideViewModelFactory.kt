package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.network.GuideRepository

class GuideViewModelFactory(private val guideRepository: GuideRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuideViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GuideViewModel(guideRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}