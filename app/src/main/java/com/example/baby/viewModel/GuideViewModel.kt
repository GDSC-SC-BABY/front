package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.Activity
import com.example.baby.data.Guide
import com.example.baby.network.GuideRepository
import com.example.baby.network.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class GuideViewModel(private val guideRepository: GuideRepository):ViewModel() {
    private val _guideDataState = MutableStateFlow<Resource<List<Guide>>>(Resource.loading(null))
    val guideDataState: StateFlow<Resource<List<Guide>>> = _guideDataState


    suspend fun getGuideList() {
        viewModelScope.launch {
            _guideDataState.value = Resource.loading(null)
            try {
                val response = guideRepository.getGuide()
                if (response.isSuccessful && response.body() != null) {
                    _guideDataState.value = Resource.success(response.body())
                } else {
                    _guideDataState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _guideDataState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}