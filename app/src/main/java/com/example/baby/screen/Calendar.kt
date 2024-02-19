package com.example.baby.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.CalendarDate
import com.example.baby.util.RecordSelectDialog
import com.example.baby.viewModel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CustomCalendarView(
    viewModel: CalendarViewModel,
    textHeight: Int = 50,
    navController: NavController
) {
    val showDialog = remember { mutableStateOf(false) }
    val daysInMonth by viewModel.calendarDays.observeAsState(emptyList())

    val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())

    Column() {
        MonthWidget(viewModel = viewModel)
        WeekDayHeaders()
        CustomCalendarLayout(textHeight = textHeight) {
            daysInMonth.forEach { calendarDay ->
                DateCell(calendarDay, dateFormatter) {
                    showDialog.value = true
                }
            }
        }
    }

    if (showDialog.value) {
        RecordSelectDialog(navController = navController, onDismiss = { showDialog.value = false })
    }
}

@Composable
fun CustomCalendarLayout(
    textHeight: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier.padding(horizontal = 2.dp)
    ) { measurable, constraints ->
        val cellWidth = constraints.maxWidth / 7
        val cellHeight = constraints.maxHeight / 6

        val placeable = measurable.map { measurable ->
            measurable.measure(Constraints.fixed(cellWidth, cellHeight))
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            var yPosition = 0

            placeable.forEachIndexed { index, placeable ->
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
fun MonthWidget(viewModel: CalendarViewModel) {
    val info = viewModel.getCurrentYearAndMonth()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousMonth() }) {
                Icon(
                    painter = painterResource(id = R.drawable.previous_month_icon),
                    tint = colorResource(id = R.color.secondary_light),
                    contentDescription = "달 -1"
                )
            }

            Text(
                text = info,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.secondary_color)
            )

            IconButton(onClick = { viewModel.nextMonth() }) {
                Icon(
                    painter = painterResource(id = R.drawable.next_month_icon),
                    tint = colorResource(id = R.color.secondary_light),
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
            .width(32.dp) // WeekDayHeaders에서 사용한 것과 동일한 너비 적용
            .padding(horizontal = 15.dp),
        fontSize = 14.sp
    )
}

@Composable
fun WeekDayHeaders() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), // CustomCalendarLayout의 패딩과 일치
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { day ->
            val textColor = when (day) {
                "Sun" -> colorResource(id = R.color.sunday)
                "Sat" -> colorResource(id = R.color.saturday)
                else -> colorResource(id = R.color.day)
            }
            Text(
                text = day,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
            )
        }
    }
}