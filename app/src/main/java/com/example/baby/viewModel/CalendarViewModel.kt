package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import com.example.baby.data.CalendarDate
import java.util.*

class CalendarViewModel : ViewModel() {
    val calendarDayList: List<CalendarDate> by lazy {
        generateCalendarDays()
    }

    // 함수 이름은 그대로 유지합니다.
    private fun generateCalendarDays(): List<CalendarDate> {
        val dates = mutableListOf<CalendarDate>()

        val calendar = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.SUNDAY
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val firstDayOfMonthWeekday = calendar.get(Calendar.DAY_OF_WEEK)
        val daysToAddFromPreviousMonth = firstDayOfMonthWeekday - Calendar.SUNDAY

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

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)

        // 이번 달의 날짜를 추가합니다.
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
}
