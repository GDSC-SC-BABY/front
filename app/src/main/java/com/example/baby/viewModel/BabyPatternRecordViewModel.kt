package com.example.baby.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.User
import com.example.baby.network.BabyPatternRepository
import java.time.LocalDate

class BabyPatternRecordViewModel: ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    val babyPatternRepository = BabyPatternRepository()

/*    fun registerPattern(user: User) {
        viewModelScope.launch {
            _userRegistrationState.value = Resource.loading(null)
            try {
                val response = babyPatternRepository.register(user)
                if (response.isSuccessful && response.body() != null) {
                    _userRegistrationState.value = Resource.success(response.body())
                } else {
                    _userRegistrationState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _userRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }*/
}