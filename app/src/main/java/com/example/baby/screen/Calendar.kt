package com.example.baby.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.baby.data.CalendarDate
import com.example.baby.viewModel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomCalendarLayout(
    textHeight: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val cellWidth = constraints.maxWidth / 7
        val cellHeight = constraints.maxHeight / 6

        val placeables = measurables.map { measurable ->
            measurable.measure(Constraints.fixed(cellWidth, cellHeight))
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            var yPosition = 0

            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = xPosition, y = yPosition)

                xPosition += cellWidth
                if (index % 7 == 6) {
                    xPosition = 0
                    yPosition += cellHeight
                }
            }
        }
    }
}

@Composable
fun CustomCalendarView(viewModel: CalendarViewModel, textHeight: Int = 50) {
    val daysInMonth = viewModel.calendarDayList
    Log.d("와이라노", daysInMonth.toString())
    val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())

    Column() {
        WeekDayHeaders()
        CustomCalendarLayout(textHeight = textHeight, ) {
            daysInMonth.forEach { calendarDay ->
                DateCell(calendarDay, dateFormatter)
            }
        }
    }
}

@Composable
fun DateCell(calendarDay: CalendarDate, dateFormatter: SimpleDateFormat) {
    val textColor = if (calendarDay.isCurrentMonth) Color.Black else Color.Gray
    Text(
        text = dateFormatter.format(calendarDay.date),
        color = textColor,
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun WeekDayHeaders() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}
