package com.example.baby.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class LoadingViewModel() : ViewModel() {

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    init {
        startSplashScreenTimer()
    }

    private fun startSplashScreenTimer() {
        viewModelScope.launch {
            delay(3000) // 3초 대기
            _navigateToMainScreen.value = true // LiveData를 업데이트하여 UI에 알림
        }
    }
}
