package com.example.baby.screen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.ButtonColors

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.baby.R
import com.example.baby.data.*
import com.example.baby.ui.theme.MainFontStyle
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.ui.theme.nanumSquare
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.BabyPatternRecordViewModel
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyPatternRecordPage(
    viewModel: BabyPatternRecordViewModel,
    navController: NavController,
    selectedIndex: Int
) {
    // 현재 선택된 탭의 인덱스를 저장하는 상태 변수
    var selectedTab by remember { mutableStateOf(TabType.values()[selectedIndex]) }
    var selectedDate: LocalDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime: LocalTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime: LocalTime by remember { mutableStateOf(LocalTime.now()) }
    var memo: String? by remember { mutableStateOf("") }
    var medicineType: String? by remember { mutableStateOf("") }
    var defecationStatus: String? by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "생활 패턴 기록",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.sub_color))
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            CustomTabRow(selectedTabIndex = selectedTab.ordinal) { index ->
                selectedTab = TabType.values()[index]
            }
            Divider(
                thickness = 2.dp,
                color = colorResource(id = R.color.gray1)
            )
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                PatternTitle(selectedTab)
                Spacer(modifier = Modifier.height(20.dp))
                BabyPatternTime(viewModel)
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
                    TabType.Defecation -> Column {
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
//            WriteSignificant()
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
}

@Composable
fun PatternTitle(selectedTab: TabType) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "오늘 한 육아 활동",
            style = StartFontStyle.startSubtitle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(20.dp)
                .weight(3f)
        )
        Spacer(
            modifier = Modifier
                .width(20.dp)
                .weight(1f)
        )
        TextField(
            value = selectedTab.content,
            onValueChange = { /* Handle title change */ },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.sub_color),
                focusedBorderColor = Color.Unspecified,
                cursorColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified,
                textColor = colorResource(id = R.color.secondary_color)
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = StartFontStyle.startSubtitle,
            modifier = Modifier
                .width(20.dp)
                .weight(4f)
        )
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
    viewModel: BabyPatternRecordViewModel
) {
    Column {
        Text(text = "생활패턴 시간", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        DateAndTimePicker(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimePicker(
    viewModel: BabyPatternRecordViewModel,
) {
    val date by viewModel.date.collectAsState()

    val hour by viewModel.hour.collectAsState()
    val minute by viewModel.minute.collectAsState()

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var isAM by remember { mutableStateOf(true) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = "${date.year}/${date.monthValue}/${date.dayOfMonth}",
                onValueChange = { },
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.background_gray),
                    focusedBorderColor = Color.Unspecified,
                    cursorColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    textColor = colorResource(id = R.color.secondary_color)
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = StartFontStyle.startSubtitle,
                modifier = Modifier
                    .weight(1f),
                leadingIcon = {
                    IconButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.calender_icon),
                            "",
                            tint = colorResource(id = R.color.secondary_color)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(1f),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.background_gray),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 0.dp,
                            color = colorResource(id = R.color.background_gray),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    OutlinedTextField(
                        value = if (hour != 0) hour.toString() else "",
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || (newValue.length <= 2 && newValue.all { it.isDigit() })) {
                                val newHour = newValue.toIntOrNull()
                                if (newHour != null && newHour in 0..23) {
                                    viewModel.hour.value = newHour
                                } else if (newValue.isEmpty()) {
                                    viewModel.hour.value = 0 // 텍스트 필드가 비어있을 때 0으로 설정
                                }
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = colorResource(id = R.color.background_gray),
                            focusedBorderColor = Color.Unspecified,
                            cursorColor = Color.Unspecified,
                            unfocusedBorderColor = Color.Unspecified,

                            textColor = colorResource(id = R.color.secondary_color)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(
                            onDone = {

                            }
                        ),
                        textStyle = TextStyle(
                            fontFamily = nanumSquare,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        ":",
                        style = StartFontStyle.startSubtitle,
                        color = colorResource(id = R.color.secondary_color),
                    )
                    OutlinedTextField(
                        value = if (minute != 0) minute.toString() else "",
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || (newValue.length <= 2 && newValue.all { it.isDigit() })) {
                                val newMinute = newValue.toIntOrNull()
                                if (newMinute != null && newMinute in 0..59) {
                                    viewModel.minute.value = newMinute
                                } else if (newValue.isEmpty()) {
                                    viewModel.minute.value = 0 // 텍스트 필드가 비어있을 때 0으로 설정
                                }
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = colorResource(id = R.color.background_gray),
                            focusedBorderColor = Color.Unspecified,
                            cursorColor = Color.Unspecified,
                            unfocusedBorderColor = Color.Unspecified,
                            textColor = colorResource(id = R.color.secondary_color)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // Handle keyboard 'Done' action if needed
                            }
                        ),
                        textStyle = StartFontStyle.startSubtitle,
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    androidx.compose.material3.TextButton(
                        onClick = { isAM = !isAM },
/*                        colors = ButtonColors(

                        )*/
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (isAM) "AM" else "PM")
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                viewModel.date.value = it
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
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabType.values().forEachIndexed { index, tabType ->
            val isSelected = index == selectedTabIndex
            if (tabType != TabType.BabyFood) {
                OutlinedButton(
                    onClick = { onTabSelected(index) },
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSelected) colorResource(id = R.color.background_main) else colorResource(
                            id = R.color.white
                        ),
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                    ),
                    border = BorderStroke(0.dp, Color.White),
                    //modifier = Modifier.height(80.dp).width(90.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = tabType.icon),
                            contentDescription = "Tab Icon",
                            tint = tabType.backColor,
                            modifier = Modifier
                                .size(35.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            tabType.title,
                            style = MainFontStyle.body2,
                            fontSize = 13.sp,
                            color = colorResource(id = R.color.gray6)
                        )
                    }
                }
            }
        }
        Divider(
            thickness = 2.dp,
            color = colorResource(id = R.color.gray3)
        )
    }
}

enum class TabType(
    val title: String,
    val keyword: String,
    val content: String,
    val icon: Int,
    val backColor: Color
) {
    Defecation("배변 활동", "Defecation", "기저귀 교체", R.drawable.icon_defecation, Color(0xFFF18CAE)),
    BabyFood("맘마 타임", "BabyFood", "상세 내용을 작성해주세요.", R.drawable.icon_meal, Color(0xFF80C9E9)),
    Medicine("투약 하기", "Medicine", "약 먹여주기", R.drawable.icon_medicine, Color(0xFFFA572A)),
    Sleep("수면 활동", "Sleep", "잠 재우기", R.drawable.icon_sleep, Color(0xFF00539C)),
    Bath("목욕 하기", "Bath", "깨끗이 씻겨주기", R.drawable.icon_bath, Color(0xFF0094D4));

    companion object {
        fun fromTitle(title: String): TabType? {
            return values().find { it.title == title }
        }
    }
}

