package com.example.baby.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baby.data.CalendarDate
import java.util.*

class CalendarViewModel : ViewModel() {
    private val _calendarDays = MutableLiveData<List<CalendarDate>>()
    val calendarDays: LiveData<List<CalendarDate>> = _calendarDays

    private val calendar = Calendar.getInstance()

    init {
        updateCalendarDays()
    }

    private fun updateCalendarDays() {
        _calendarDays.value = generateCalendarDays()
    }

    private fun generateCalendarDays(): List<CalendarDate> {
        val dates = mutableListOf<CalendarDate>()
        calendar.apply {
            firstDayOfWeek = Calendar.SUNDAY
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val firstDayOfMonthWeekday = calendar.get(Calendar.DAY_OF_WEEK)
        val daysToAddFromPreviousMonth = firstDayOfMonthWeekday - Calendar.SUNDAY
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        // 이전 달의 날짜를 추가합니다.
        if (daysToAddFromPreviousMonth > 0) {
            val previousMonth = if (month == Calendar.JANUARY) Calendar.DECEMBER else month - 1
            val previousYear = if (month == Calendar.JANUARY) year - 1 else year
            calendar.set(previousYear, previousMonth, 1)
            val daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            for (i in daysInPreviousMonth - daysToAddFromPreviousMonth + 1..daysInPreviousMonth) {
                calendar.set(Calendar.YEAR, previousYear)
                calendar.set(Calendar.MONTH, previousMonth)
                calendar.set(Calendar.DAY_OF_MONTH, i)
                dates.add(CalendarDate(calendar.time, isCurrentMonth = false))
            }
        }

        calendar.set(year, month, 1)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            dates.add(CalendarDate(calendar.time, isCurrentMonth = true))
        }

        // 다음 달 날짜를 계산합니다.
        calendar.set(year, month + 1, 1)
        val daysToAddFromNextMonth = 7 - calendar.get(Calendar.DAY_OF_WEEK) + Calendar.SUNDAY
        for (i in 1..daysToAddFromNextMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            dates.add(CalendarDate(calendar.time, isCurrentMonth = false))
        }

        return dates
    }

    fun getCurrentYearAndMonth(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        return String.format(Locale.getDefault(), "%d년 %02d월", year, month)
    }

    fun previousMonth() {
        calendar.add(Calendar.MONTH, -1)
        updateCalendarDays()
        Log.d("PreviousMonth", Calendar.MONTH.toString())
    }

    fun nextMonth() {
        calendar.add(Calendar.MONTH, 1)
        updateCalendarDays()
        Log.d("NextMonth", Calendar.MONTH.toString())
    }
}