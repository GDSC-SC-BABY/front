package com.example.baby.screen

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.viewModel.BabyRegisterViewModel
import java.util.*

@Composable
fun BabyRegisterScreen(viewModel: BabyRegisterViewModel, navController: NavController) {
    val isFormValid by viewModel.isFormValid.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("자녀 등록", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        BabyNameRegisterField(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        BirthdayRegisterField(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        BabyInfoRegisterWidget(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        BabyGenderRegisterWidget(viewModel)
        BabyRegisterButton(
            viewModel = viewModel,
            isNotNull = isFormValid,
            text = "로그인",
            route = "mainScreen",
            navController = navController
        )
    }
}

@Composable
fun BabyNameRegisterField(viewModel: BabyRegisterViewModel) {
    val text by viewModel.babyName.collectAsState()

    OutlinedTextField(
        value = text,
        onValueChange = { updatedName ->
            viewModel.babyName.value = updatedName
        },
        label = { Text("이름") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@Composable
fun BirthdayRegisterField(viewModel: BabyRegisterViewModel) {
    var text by remember { mutableStateOf("생일을 선택하세요.") }
    val context = LocalContext.current // Composable 함수 내부에서 사용

    TextButton(onClick = {
        showDatePicker(context) { year, month, dayOfMonth ->
            // 사용자가 날짜를 선택하면 텍스트 업데이트
            text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            viewModel.birth.value = text
        }

    }) {
        Text(text, fontSize = 15.sp)
    }
}

fun showDatePicker(
    context: Context,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    // 현재 날짜를 기본값으로 설정
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // 날짜 선택기 다이얼로그 생성 및 표시
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, dayOfMonth ->
            onDateSelected(selectedYear, selectedMonth, dayOfMonth)
        }, year, month, day
    )
    datePickerDialog.show()
}

@Composable
fun BabyGenderRegisterWidget(viewModel: BabyRegisterViewModel) {
    val selectedOption by viewModel.gender.collectAsState()
    Column() {
        RadioButton(selected = selectedOption == "남자", onClick = {
            viewModel.gender.value = "남자"
        })
        Text("남자")
        RadioButton(selected = selectedOption == "여자", onClick = {
            viewModel.gender.value = "여자"
        })
        Text("여자")
    }
}

@Composable
fun BabyInfoRegisterWidget(viewModel: BabyRegisterViewModel) {
    val heightText by viewModel.height.collectAsState()
    val weightText by viewModel.weight.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = heightText,
            onValueChange = { height ->
                if (height.all { it.isDigit() || it == '.' } && height.count { it == '.' } <= 1) {
                    viewModel.height.value = height
                }
            },
            label = { Text("키 (cm)") },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = weightText,
            onValueChange = { weight ->
                if (weight.all { it.isDigit() || it == '.' } && weight.count { it == '.' } <= 1) {
                    viewModel.weight.value = weight
                }
            },
            label = { Text("몸무게 (kg)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}

@Composable
fun BabyRegisterButton(
    viewModel: BabyRegisterViewModel,
    isNotNull: Boolean,
    text: String,
    route: String,
    navController: NavController
) {
    val name by viewModel.babyName.collectAsState()
    val birth by viewModel.birth.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()

    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
//                viewModel.registerUser(user)
                viewModel.setBabyInfoToSP(context, name, birth, gender, weight, height)
                navController.navigate(route)
            },
            enabled = isNotNull
        ) {
            Text(text)
        }
    }
}