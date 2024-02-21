package com.example.baby.screen

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.Baby
import com.example.baby.network.Resource
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.ui.theme.nanumSquare
import com.example.baby.viewModel.BabyRegisterViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyRegisterScreen(
    context: Context,
    viewModel: BabyRegisterViewModel,
    navController: NavController,
    onSuccess: Unit
) {
    val isFormValid by viewModel.isFormValid.collectAsState()
    var appName = context.resources.getString(R.string.app_name)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "자녀 등록",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
                },
                //colors = Color(R.color.white)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 10.dp, bottom = 30.dp, start = 30.dp, end = 30.dp),
        ) {
            Text("아이의 이름은 무엇인가요?", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            BabyNameRegisterField(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Text("아이의 성별을 지정해주세요.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            BabyGenderRegisterWidget(viewModel)
            //BirthdayRegisterField(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Text("아이의 키와 몸무게를 설정해주세요.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            BabyInfoRegisterWidget(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Text("아이의 생일을 알려주세요.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            showDatePicker(viewModel)
            BabyRegisterButton(
                viewModel = viewModel,
                isNotNull = isFormValid,
                text = "${appName} 시작하기",
                route = "mainScreen",
                navController = navController,
                onSuccess = onSuccess
            )
        }
    }
}

@Composable
fun BabyNameRegisterField(viewModel: BabyRegisterViewModel) {
    val text by viewModel.babyName.collectAsState()

    OutlinedTextField(
        value = text,
        onValueChange = { updatedBabyName ->
            viewModel.babyName.value = updatedBabyName
        },
        placeholder = {
            Text(
                "이름을 입력하세요.",
                style = StartFontStyle.startBody1,
                color = Color(R.color.gray4)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.background_gray),
            focusedBorderColor = Color.Unspecified,
            cursorColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified
        ),
        shape = RoundedCornerShape(12.dp)
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showDatePicker(
    viewModel: BabyRegisterViewModel,
) {
    // 현재 날짜를 기본값으로 설정
    val year by viewModel.year.collectAsState()
    val month by viewModel.month.collectAsState()
    val day by viewModel.day.collectAsState()

    var expandedYear by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }
    var expandedDay by remember { mutableStateOf(false) }
    val yearList = (2024 downTo 2005).map { it.toString() }
    val monthList = (12 downTo 1).map { it.toString() }
    val dayList = (31 downTo 1).map { it.toString() }

    val selectedYearIndex = yearList.indexOf(year)
    val selectedMonthIndex = monthList.indexOf(month)
    val selectedDayIndex = dayList.indexOf(day)


    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedYear,
            onExpandedChange = {
                expandedYear = !expandedYear
            },
            modifier = Modifier.weight(3f)
        ) {
            OutlinedTextField(
                textStyle = StartFontStyle.startButton,
                value = yearList.getOrElse(selectedYearIndex) { LocalDate.now().year.toString() },
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown arrow",
                        Modifier.clickable { expandedYear = true }
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.background_gray),
                    focusedBorderColor = Color.Unspecified,
                    cursorColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                modifier = Modifier
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
            )
            DropdownMenu(
                expanded = expandedYear,
                modifier = Modifier
                    .width(150.dp)
                    .height(500.dp)
                    .background(colorResource(id = R.color.background_gray)),
                onDismissRequest = { expandedYear = false },
                properties = PopupProperties(focusable = false)
            ) {
                yearList.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.year.value = yearList[index]
                            expandedYear = false
                        }
                    ) {
                        Text(
                            text = "$text", style = StartFontStyle.startBody1,
                            color = Color(R.color.gray4)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(9.dp))
        ExposedDropdownMenuBox(
            expanded = expandedMonth,
            onExpandedChange = {
                expandedMonth = !expandedMonth
            },
            modifier = Modifier.weight(2f)
        ) {
            OutlinedTextField(
                textStyle = StartFontStyle.startButton,
                value = monthList.getOrElse(selectedMonthIndex) { LocalDate.now().month.value.toString() },
                onValueChange = { },
                readOnly = true, // This makes the TextField not editable
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown arrow",
                        Modifier.clickable { expandedMonth = true }
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.background_gray),
                    focusedBorderColor = Color.Unspecified,
                    cursorColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                modifier = Modifier
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
            )
            DropdownMenu(
                expanded = expandedMonth,
                modifier = Modifier
                    .width(150.dp)
                    .height(500.dp)
                    .background(colorResource(id = R.color.background_gray)),
                onDismissRequest = { expandedMonth = false },
                properties = PopupProperties(focusable = false)
            ) {
                monthList.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.month.value = monthList[index]
                            expandedMonth = false
                        }
                    ) {
                        Text(
                            text = "$text", style = StartFontStyle.startBody1,
                            color = Color(R.color.gray4)
                        )
                    }
                }
            }

        }
        Spacer(modifier = Modifier.width(9.dp))
        ExposedDropdownMenuBox(
            expanded = expandedMonth,
            onExpandedChange = {
                expandedDay = !expandedDay
            },
            modifier = Modifier.weight(2f)
        ) {
            OutlinedTextField(
                textStyle = StartFontStyle.startButton,
                value = monthList.getOrElse(selectedDayIndex) { LocalDate.now().dayOfMonth.toString() },
                onValueChange = { },
                readOnly = true, // This makes the TextField not editable
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown arrow",
                        Modifier.clickable { expandedDay = true }
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.background_gray),
                    focusedBorderColor = Color.Unspecified,
                    cursorColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                modifier = Modifier
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
            )
            DropdownMenu(
                expanded = expandedDay,
                modifier = Modifier
                    .width(150.dp)
                    .height(500.dp)
                    .background(colorResource(id = R.color.background_gray)),
                onDismissRequest = { expandedDay = false },
                properties = PopupProperties(focusable = false)
            ) {
                dayList.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.day.value = dayList[index]
                            expandedDay = false
                        }
                    ) {
                        Text(
                            text = "$text", style = StartFontStyle.startBody1,
                            color = Color(R.color.gray4)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun BabyGenderRegisterWidget(viewModel: BabyRegisterViewModel) {
    val gender = listOf(
        "남자",
        "여자"
    )
    val selectedOption by viewModel.gender.collectAsState()
    Row(modifier = Modifier.fillMaxWidth()) {
        gender.forEach { gender ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (gender == selectedOption) {
                        colorResource(id = R.color.brand_color)
                    } else {
                        colorResource(id = R.color.background_gray)
                    },
                    contentColor = colorResource(id = R.color.secondary_color),
                ),
                onClick = {
                    viewModel.gender.value = gender
                },
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Text(
                    text = gender,
                    style = StartFontStyle.startButton,
                    color = if (gender == selectedOption) {
                        colorResource(id = R.color.secondary_color)
                    } else {
                        colorResource(id = R.color.gray4)
                    },
                )
            }
        }
    }
}

@Composable
fun BabyInfoRegisterWidget(viewModel: BabyRegisterViewModel) {
    val heightText by viewModel.height.collectAsState()
    val weightText by viewModel.weight.collectAsState()

    Row {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            value = heightText,
            onValueChange = { height ->
                if (height.all { it.isDigit() || it == '.' } && height.count { it == '.' } <= 1) {
                    viewModel.height.value = height
                }
            },
            placeholder = {
                Text(
                    "키 (cm)",
                    textAlign = TextAlign.Center,
                    style = StartFontStyle.startButton,
                    color = Color(R.color.gray4),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.background_gray),
                focusedBorderColor = Color.Unspecified,
                cursorColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.width(9.dp))
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            value = weightText,
            onValueChange = { weight ->
                if (weight.all { it.isDigit() || it == '.' } && weight.count { it == '.' } <= 1) {
                    viewModel.weight.value = weight
                }
            },
            placeholder = {
                Text(
                    "몸무게 (kg)",
                    style = StartFontStyle.startButton,
                    color = Color(R.color.gray4),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.background_gray),
                focusedBorderColor = Color.Unspecified,
                cursorColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun BabyRegisterButton(
    viewModel: BabyRegisterViewModel,
    isNotNull: Boolean,
    text: String,
    route: String,
    navController: NavController,
    onSuccess: Unit
) {
    val name by viewModel.babyName.collectAsState()
    val birth by viewModel.birth.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()
    val year by viewModel.year.collectAsState()

    val babyRegisterState = viewModel.babyRegistrationState.collectAsState().value

    Log.d("year", year)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                viewModel.registerBaby(
                    Baby(
                        name = name,
                        gender = gender,
                        birthWeight = weight,
                        birthHeight = height,
                        imageUrl = "",
                        dateTime = LocalDateTime.of(
                            LocalDate.of(
                                viewModel.year.value.toInt(),
                                viewModel.month.value.toInt(),
                                viewModel.day.value.toInt()
                            ),
                            LocalTime.MIDNIGHT
                        )
                    )
                )
                viewModel.setBabyInfoToSP(context, name, birth, gender, weight, height)
                onSuccess
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = colorResource(id = R.color.secondary_color),
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Text(
                text,
                style = StartFontStyle.startButton,
                color = colorResource(id = R.color.secondary_color)
            )
        }

    }

    LaunchedEffect(babyRegisterState) {
        when (babyRegisterState) {
            is Resource.Success -> {
                navController.navigate(route)
            }
            is Resource.Error -> {
                // 오류가 발생한 경우 로그 출력
                Log.d("RegisterButton", "API 오류: ${babyRegisterState.message}")
            }
            is Resource.Loading -> {
                // 필요한 경우 로딩 상태 처리
            }
        }
    }

}