package com.example.baby.viewModel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
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


class BabyFoodRegisterViewModel() : ViewModel() {

    val nickname = MutableStateFlow("")
    val mealTime = MutableStateFlow("")

    private val _toppings = mutableStateListOf<String>()
    val toppings: List<String> = _toppings

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> = _selectedImage

    fun onImagePicked(uri: Uri?) {
        _selectedImage.value = uri
    }

    // 토핑 추가 함수
    fun addTopping() {
        _toppings.add("") // 새로운 토핑(빈 문자열) 추가
    }

    // 특정 인덱스의 토핑을 업데이트하는 함수
    fun updateTopping(index: Int, topping: String) {
        if (index in _toppings.indices) {
            _toppings[index] = topping
        }
    }

    val isFormValid: StateFlow<Boolean> = combine(nickname, mealTime) { nickname, relationship ->
        nickname.isNotBlank() && relationship.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}
