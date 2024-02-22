package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.Activity
import com.example.baby.data.User
import com.example.baby.network.BabyPatternRepository
import com.example.baby.network.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BabyPatternViewModel(private val babyPatternRepository: BabyPatternRepository) : ViewModel() {
    private val _patternDataState = MutableStateFlow<Resource<List<Activity>>>(Resource.loading(null))
    val patternDataState: StateFlow<Resource<List<Activity>>> = _patternDataState


    fun getBabyPatternWithDate(babyId: Int, date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        //var babyId =
        viewModelScope.launch {
            _patternDataState.value = Resource.loading(null)
            try {
                val response = babyPatternRepository.getActivitiesByDate(babyId = babyId, date.format(formatter))
                if (response.isSuccessful && response.body() != null) {
                    _patternDataState.value = Resource.success(response.body())
                } else {
                    _patternDataState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _patternDataState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}