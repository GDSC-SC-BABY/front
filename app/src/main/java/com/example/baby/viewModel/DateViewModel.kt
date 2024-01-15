package com.example.baby.viewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class DateViewModel() : ViewModel() {

    private val _birthday = MutableLiveData<String>()
    val birthday: LiveData<String> = _birthday

    init {
        // 초기 날짜를 오늘로 설정
        _birthday.value = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    // 날짜를 업데이트하는 메서드
    fun updateBirthday(newDate: LocalDate) {
        _birthday.value = newDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
