package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baby.dao.TodoDao
import com.example.baby.network.TodoRepository

class DateViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DateViewModel::class.java)) {
            return DateViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}