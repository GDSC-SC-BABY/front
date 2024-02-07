package com.example.baby.screen

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.baby.viewModel.BabyPatternRecordViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun BabyPatternRecordPage(viewModel: BabyPatternRecordViewModel, navController: NavController, context: Context) {
    // 현재 선택된 탭의 인덱스를 저장하는 상태 변수
    var selectedTab by remember { mutableStateOf(TabType.Pee) }

    Scaffold(
        topBar = {
            CustomTabRow(selectedTabIndex = selectedTab.ordinal) { index ->
                selectedTab = TabType.values()[index]
            }
        },
        content = { innerPadding ->
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
            ) {
                Text("제목", style = MaterialTheme.typography.h6)
                TextField(
                    value = selectedTab.title,
                    onValueChange = { /* Handle title change */ },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                BabyPatternTime(selectedTab.ordinal,{
                    viewModel.setSelectedDate(it)
                }, context)
                Spacer(modifier = Modifier.height(20.dp))
                WriteSignificant()
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("등록하기")
                }
            }
        }
    )
}
@Composable
fun BabyPatternTime(selectedTabIndex: Int, onDateSelected: (LocalDate) -> Unit,    context: Context) {
    Column {
        Text(text = "생활패턴 시간", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        DatePickerWithButton(LocalDate.now(), onDateSelected, context)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithButton(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    context: Context
) {
    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${selectedDate.year} ${selectedDate.monthValue} ${selectedDate.dayOfMonth}",
                modifier = Modifier.weight(2f),
                fontSize = 18.sp

            )
            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Rounded.Notifications,"")
            }
        }
    }

    if(showDatePicker){
        MyDatePickerDialog(
            onDateSelected = { date = it },

            onDismiss = { showDatePicker = false }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        formatter.format(Date(it))
    } ?: ""

    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
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

