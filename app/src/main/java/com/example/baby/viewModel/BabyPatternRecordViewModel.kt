package com.example.baby.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.*
import com.example.baby.network.BabyPatternRepository
import com.example.baby.network.Resource
import com.example.baby.screen.TabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class BabyPatternRecordViewModel: ViewModel() {

    private val _registerState = MutableStateFlow<Resource<PatternResponse>>(Resource.loading(null))
    val registerState: StateFlow<Resource<PatternResponse>> = _registerState

    private val _selectedDate = MutableLiveData<LocalDate>(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    private val babyPatternRepository = BabyPatternRepository()


    fun registerPattern(pattern: Any) {
        viewModelScope.launch {
            _registerState.value = Resource.loading(null)
            try {
                val response = when (pattern) {
                    is SleepPattern -> babyPatternRepository.registerSleepPattern(pattern)
                    is MedicinePattern -> babyPatternRepository.registerMedicinePattern(pattern)
                    //is DefecationPattern -> babyPatternRepository.registerDefecationPattern(pattern)
                    //is BathPattern -> babyPatternRepository.registerBathPattern(pattern)
                    else -> throw IllegalArgumentException("Unknown pattern type")
                }

                if (response.isSuccessful && response.body() != null) {
                    _registerState.value = Resource.success(response.body())
                } else {
                    _registerState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _registerState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}
