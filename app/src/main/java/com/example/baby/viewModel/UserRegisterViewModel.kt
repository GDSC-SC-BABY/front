package com.example.baby.viewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class UserRegisterViewModel() : ViewModel() {

    val nickname = MutableStateFlow("")
    val relationship = MutableStateFlow("")

    // isFormValid는 nickname과 relationship으로부터 계산된 StateFlow입니다.
    val isFormValid: StateFlow<Boolean> = combine(nickname, relationship) { nickname, relationship ->
        nickname.isNotBlank() && relationship.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}
