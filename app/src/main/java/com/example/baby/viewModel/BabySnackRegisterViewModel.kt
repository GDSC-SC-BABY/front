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


class BabySnackRegisterViewModel() : ViewModel() {

    val nickname = MutableStateFlow("")
    val snackTime = MutableStateFlow("")
    val amount = MutableStateFlow("")
    val significant = MutableStateFlow("")

    private val _snacks = mutableStateListOf<String>()
    val snacks: List<String> = _snacks

    private val _drinks = mutableStateListOf<String>()
    val drinks: List<String> = _drinks

    private val _snackAmounts = mutableStateListOf<Int>()
    val snackAmounts: List<Int> = _snackAmounts

    private val _drinkAmounts = mutableStateListOf<Int>()
    val drinkAmounts: List<Int> = _drinkAmounts

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> = _selectedImage

    fun onImagePicked(uri: Uri?) {
        _selectedImage.value = uri
    }

    // 토핑 추가 함수
    fun addSnack() {
        _snacks.add("") // 새로운 토핑(빈 문자열) 추가
    }

    // 특정 인덱스의 토핑을 업데이트하는 함수
    fun updateSnack(index: Int, snack: String) {
        if (index in _drinks.indices) {
            _drinks[index] = snack
        }
    }

    fun addSnackAmounts() {
        _snackAmounts.add(0)
    }

    fun updateSnackAmounts(index: Int, amount: Int) {
        if (index in _snackAmounts.indices) {
            _snackAmounts[index] = amount
        }
    }

    fun setSnackTime(time: String) {
        snackTime.value = time
    }

    fun setSnackAmount(snackAmount: String) {
        amount.value = snackAmount
    }

    fun setSignificant(content: String) {
        significant.value = content
    }

    // 토핑 추가 함수
    fun addDrink() {
        _drinks.add("") // 새로운 토핑(빈 문자열) 추가
    }

    // 특정 인덱스의 토핑을 업데이트하는 함수
    fun updateDrink(index: Int, drink: String) {
        if (index in _drinks.indices) {
            _drinks[index] = drink
        }
    }
    fun addDrinkAmounts() {
        _drinkAmounts.add(0)
    }

    fun updateDrinkAmounts(index: Int, amount: Int) {
        if (index in _drinkAmounts.indices) {
            _drinkAmounts[index] = amount
        }
    }

    val isFormValid: StateFlow<Boolean> = combine(nickname, snackTime) { nickname, relationship ->
        nickname.isNotBlank() && relationship.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}
