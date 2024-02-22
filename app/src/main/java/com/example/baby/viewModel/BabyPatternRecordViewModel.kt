package com.example.baby.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.time.LocalTime

class BabyPatternRecordViewModel(private val babyPatternRepository: BabyPatternRepository): ViewModel() {

    private val _registerState = MutableStateFlow<Resource<PatternResponse>>(Resource.loading(null))
    val registerState: StateFlow<Resource<PatternResponse>> = _registerState

    private val _selectedDate = MutableLiveData<LocalDate>(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    val date = MutableStateFlow(LocalDate.now())
    val hour = MutableStateFlow(LocalTime.now().hour)
    val minute = MutableStateFlow(LocalTime.now().minute)

    val memo = MutableStateFlow("")

    var medicineType = MutableStateFlow("")


    fun setMedicine(medicine: String) {
        medicineType.value = medicine
    }

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
