package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.CalendarDate
import com.example.baby.util.RecordSelectDialog
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.CalendarViewModel
import java.text.SimpleDateFormat
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CustomCalendarView(
    viewModel: CalendarViewModel,
    textHeight: Int = 50,
    navController: NavController
) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val daysInMonth by viewModel.calendarDays.observeAsState(emptyList())
    val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.sub_color),
                elevation = 0.dp,
                title = {
                    Text(
                        "맘마타임",
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.secondary_color),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
    ) { innerPadding ->
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    Modifier.background(color = colorResource(id = R.color.background_main))
                ) {
                    MonthWidget(viewModel = viewModel)
                    WeekDayHeaders()
                    CustomCalendarLayout(textHeight = textHeight) {
                        daysInMonth.forEach { calendarDay ->
                            DateCell(calendarDay, dateFormatter) {
                                selectedDate.value =
                                    viewModel.convertDateToLocalDate(calendarDay.date)
                                showDialog.value = true
                            }
                        }
                    }
                }
                Box(Modifier.padding(horizontal = 15.dp, vertical = 15.dp)) {
                    WriteMemo()
                }
            }

            if (showDialog.value) {
                RecordSelectDialog(
                    navController = navController,
                    selectedDate = selectedDate.value,
                    onDismiss = { showDialog.value = false })
            }
        }
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
    ) { measurables, constraints ->

        val cellWidth = constraints.maxWidth / 7
        val cellHeight = constraints.maxWidth / 5

        val placeables = measurables.map { measurable ->
            measurable.measure(Constraints.fixed(width = cellWidth, height = cellHeight))
        }

        val calendarHeight = cellHeight * (placeables.size / 7)

        layout(constraints.maxWidth, calendarHeight) {
            var xPosition = 0
            var yPosition = 0

            placeables.forEachIndexed { index, placeable ->
                placeable.place(x = xPosition, y = yPosition)

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
    val currentYearAndMonth by remember { viewModel.currentYearAndMonthState }

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
                text = currentYearAndMonth,
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
    val textColor = if(calendarDay.isCurrentMonth) colorResource(id = R.color.gray4) else colorResource(id = R.color.gray2)

    Text(
        text = dateFormatter.format(calendarDay.date),
        color = textColor,
        modifier = Modifier
            .padding(4.dp)
            .clickable(enabled = calendarDay.isCurrentMonth, onClick = onClick)
            .width(32.dp)
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
            .padding(horizontal = 2.dp),
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

@Composable
fun WriteMemo() {
    val context = LocalContext.current
    val memo = remember {
        mutableStateOf(SharedPreferenceUtil(context).getString("memo", ""))
    }

    Box(
        modifier = Modifier
            .background(
                colorResource(id = R.color.sub_color),
                shape = RoundedCornerShape(15.dp)
            )
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Memo",
                    color = colorResource(id = R.color.secondary_color),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 20.dp)
                )
                IconButton({
                    SharedPreferenceUtil(context).setString("memo", "")
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "delete memo")
                }
            }
            memo.value?.let {
                TextField(
                    value = it,
                    onValueChange = { newText ->
                        memo.value = newText
                        SharedPreferenceUtil(context).setString("memo", newText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    placeholder = {
                        Text(
                            "메모할 것들을 적어요!",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.secondary_light)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.secondary_color)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = false,
                    maxLines = 6
                )
            }
        }
    }
}