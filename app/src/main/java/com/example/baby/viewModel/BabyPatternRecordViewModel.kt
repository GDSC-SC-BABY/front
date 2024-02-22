package com.example.baby.viewModel

import android.util.Log
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
import com.example.baby.util.SharedPreferenceUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BabyPatternRecordViewModel(private val babyPatternRepository: BabyPatternRepository) :
    ViewModel() {

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

    val startTime = MutableStateFlow(LocalDateTime.now())
    val endTime = MutableStateFlow(LocalDateTime.now())

    val memo = MutableStateFlow("")

    var medicineType = MutableStateFlow("")


    fun setMedicine(medicine: String) {
        medicineType.value = medicine
    }

    fun registerPattern(babyId: Int, pattern: TabType) {
        viewModelScope.launch {
            _registerState.value = Resource.loading(null)
            try {
                val response = when (pattern) {
                    TabType.Sleep -> babyPatternRepository.registerSleepPattern(
                        SleepPattern(
                            startTime = startTime.value,
                            endTime = endTime.value,
                            memo = memo.value,
                            babyId = babyId
                        )
                    )
                    TabType.Medicine -> babyPatternRepository.registerMedicinePattern(
                        MedicinePattern(
                            startTime = startTime.value,
                            medicineType = medicineType.value,
                            memo = memo.value,
                            babyId = babyId
                        )
                    )
                    TabType.Defecation -> babyPatternRepository.registerDefecationPattern(
                        DefecationPattern(
                            startTime = startTime.value,
                            memo = memo.value,
                            babyId = babyId
                        )
                    )
                    TabType.Bath -> babyPatternRepository.registerBathPattern(
                        BathPattern(
                            startTime = startTime.value,
                            endTime = endTime.value,
                            memo = memo.value,
                            babyId = babyId
                        )
                    )
                    else -> throw IllegalArgumentException("Unknown pattern type")
                }
                Log.d("registerPattern", response.toString())

                if (response.isSuccessful && response.body() != null) {
                    _registerState.value = Resource.success(response.body())
                } else {
                    _registerState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch (e: Exception) {
                _registerState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}
