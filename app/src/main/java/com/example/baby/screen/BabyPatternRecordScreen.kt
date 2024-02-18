package com.example.baby.screen

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.baby.R
import com.example.baby.data.*
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.BabyPatternRecordViewModel
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun BabyPatternRecordPage(
    viewModel: BabyPatternRecordViewModel,
    navController: NavController,
    selectedIndex: Int
) {
    // 현재 선택된 탭의 인덱스를 저장하는 상태 변수
    var selectedTab by remember { mutableStateOf(  TabType.values()[selectedIndex]) }
    var selectedDate: LocalDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime: LocalTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime: LocalTime by remember { mutableStateOf(LocalTime.now()) }
    var memo: String? by remember { mutableStateOf("") }
    var medicineType: String? by remember { mutableStateOf("") }
    var defecationStatus: String? by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            CustomTabRow(selectedTabIndex = selectedTab.ordinal) { index ->
                selectedTab = TabType.values()[index]
            }
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(scrollState)
        ) {
            Text("제목", style = MaterialTheme.typography.h6)
            TextField(
                value = selectedTab.title,
                onValueChange = { /* Handle title change */ },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            BabyPatternTime(selectedTab.ordinal, selectedDate) {
                selectedDate = it
            }
            when (selectedTab) {
                TabType.Sleep -> Box() {
                    Spacer(modifier = Modifier.height(20.dp))

                    // endTime
                }
                TabType.Medicine -> Column {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "투약 종류", fontWeight = FontWeight.SemiBold)
                    TextField(
                        value = medicineType ?: "",
                        onValueChange = {
                            medicineType = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                TabType.Pee -> Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "배변 상태", fontWeight = FontWeight.SemiBold)
                    TextField(
                        value = defecationStatus ?: "",
                        onValueChange = {
                            defecationStatus = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {

                }
/*                        TabType.Pee -> DefecationPattern(
                    LocalDateTime.of(selectedDate),
                    "Pee", // or any other defecation status as per your requirement
                    memo,
                    babyId
                )
                TabType.Food -> {

                }*/

            }
            Spacer(modifier = Modifier.height(20.dp))
            WriteSignificant()
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    //val babyId = SharedPreferenceUtil(context).getInt("")
                    var babyId = 1
                    val pattern = when (selectedTab) {
                        TabType.Sleep -> SleepPattern(
                            LocalDateTime.of(selectedDate, startTime),
                            LocalDateTime.of(selectedDate, endTime),
                            memo,
                            babyId
                        )
                        TabType.Medicine -> {
                            MedicinePattern(
                                LocalDateTime.of(selectedDate, startTime),
                                medicineType = medicineType,
                                memo = memo,
                                babyId = babyId
                            )
                        }
                        else -> {

                        }
/*                        TabType.Pee -> DefecationPattern(
                            LocalDateTime.of(selectedDate),
                            "Pee", // or any other defecation status as per your requirement
                            memo,
                            babyId
                        )
                        TabType.Food -> {

                        }*/

                    }
                    viewModel.registerPattern(pattern)
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("등록하기")
            }
        }
    }
}

@Composable
fun ExtraInfo(
    selectedTab: TabType,
    onValueChanged: (String) -> Unit
) {


}

@Composable
fun BabyPatternTime(
    selectedTabIndex: Int,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Column {
        Text(text = "생활패턴 시간", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        DatePickerWithButton(selectedDate, onDateSelected)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithButton(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                modifier = Modifier.weight(2f),
                fontSize = 18.sp

            )
            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Rounded.Notifications, "")
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                onDateSelected(it)
            },

            onDismiss = { showDatePicker = false }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })


    val selectedDateMillis = datePickerState.selectedDateMillis
    val selectedDate = if (selectedDateMillis != null) {
        Instant.ofEpochMilli(selectedDateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
    } else {
        LocalDate.now()
    }


    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = {
                onDateSelected(LocalDate.ofEpochDay(datePickerState.selectedDateMillis!! / (1000 * 60 * 60 * 24)))
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}


@Composable
fun CustomTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val iconList = listOf(
        R.drawable.pee_icon,
        R.drawable.meal_icon,
        R.drawable.medicine_icon,
        R.drawable.sleep_icon,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconList.forEachIndexed { index, drawable ->
            val isSelected = index == selectedTabIndex

            IconButton(
                onClick = { onTabSelected(index) }
            ) {
                Icon(
                    painter = painterResource(id = drawable),
                    contentDescription = "Tab Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(if (isSelected) 60.dp else 40.dp)
                )
            }
        }
    }
}

enum class TabType(val title: String, val content: String) {
    Pee("기저귀", "상세 내용을 작성해주세요."),
    Food("이유식", "상세 내용을 작성해주세요."),
    Medicine("복약", "상세 내용을 작성해주세요."),
    Sleep("수면", "상세 내용을 작성해주세요.")
}

