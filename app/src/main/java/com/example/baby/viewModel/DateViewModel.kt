package com.example.baby.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateViewModel() : ViewModel() {

    private val _birthday = MutableLiveData<String>()
    val birthday: LiveData<String> = _birthday

    init {
        _birthday.value = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun updateBirthday(newDate: LocalDate) {
        _birthday.value = newDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateNow(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeNow(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("hh시 mm분")

        return dateFormat.format(date)
    }

    fun parseStringToLocalDateTime(dateTimeString: String): LocalDateTime? {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
        return try {
            LocalDateTime.parse(dateTimeString, formatter)
        } catch (e: Exception) {
            null
        }
    }
}
