package com.example.baby.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class DateViewModel() : ViewModel() {

    fun getDateNow(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

//        val ddd = LocalDate.now()
//        ddd.format(DateTimeFormatter.ofPattern("yyyy년 "))

        return dateFormat.format(date)
    }
}
