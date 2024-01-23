package com.example.baby.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
fun CustomCalendarView(viewModel: CalendarViewModel, textHeight: Int = 50, navController: NavController) {
    val daysInMonth by viewModel.calendarDays.observeAsState(emptyList())

    val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())

    Column() {
        MonthWidget(viewModel = viewModel)
        WeekDayHeaders()
        CustomCalendarLayout(textHeight = textHeight) {
            daysInMonth.forEach { calendarDay ->
                DateCell(calendarDay, dateFormatter){
                    navController.navigate("foodRegisterScreen")
                }
            }
        }
    }
}

@Composable
fun MonthWidget(viewModel: CalendarViewModel) {
    val info = viewModel.getCurrentYearAndMonth()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row() {
            IconButton(onClick = { viewModel.previousMonth() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "달 -1"
                )
            }

            Text(
                text = "2024년 01월",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = { viewModel.nextMonth() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "달 +1"
                )
            }
        }
    }
}

@Composable
fun DateCell(calendarDay: CalendarDate, dateFormatter: SimpleDateFormat, onClick: () -> Unit) {
    val textColor = if (calendarDay.isCurrentMonth) Color.Black else Color.Gray
    Text(
        text = dateFormatter.format(calendarDay.date),
        color = textColor,
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
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
            val textColor = if (day == "Sun") Color.Red else Color.Black
            Text(
                text = day,
                style = MaterialTheme.typography.subtitle2,
                color = textColor,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}