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
    var dummy: List<Activity> = mutableListOf(
        Activity(
            activityId = 1,
            activityType = "Type 1",
            startTime = LocalDateTime.of(2024, 2, 15, 13, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 14, 16, 53, 203_000_000),
            specificStatus = "Status 1",
            memo = "Memo 1"
        ),
        Activity(
            activityId = 2,
            activityType = "Type 2",
            startTime = LocalDateTime.of(2024, 2, 15, 14, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 15, 16, 53, 203_000_000),
            specificStatus = "Status 2",
            memo = "Memo 2"
        ),
        Activity(
            activityId = 3,
            activityType = "Type 3",
            startTime = LocalDateTime.of(2024, 2, 15, 15, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 16, 16, 53, 203_000_000),
            specificStatus = "Status 3",
            memo = "Memo 3"
        )

    )

    private val _patternDataState = MutableStateFlow<Resource<List<Activity>>>(Resource.loading(null))
    val patternDataState: StateFlow<Resource<List<Activity>>> = _patternDataState


    fun getBabyPatternWithDate(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        //var babyId =
        viewModelScope.launch {
            _patternDataState.value = Resource.loading(null)
            try {
                val response = babyPatternRepository.getActivitiesByDate(babyId = 1, date.format(formatter))
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