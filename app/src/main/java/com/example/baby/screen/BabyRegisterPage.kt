package com.example.baby.screen

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.data.User
import com.example.baby.viewModel.LoadingViewModel
import com.example.baby.viewModel.UserRegisterViewModel
import java.util.*

@Composable
fun BabyRegisterPage(viewModel: LoadingViewModel, navController: NavController) {
    val navigateToMainScreen by viewModel.navigateToMainScreen.observeAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("자녀 등록", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        BabyNameRegisterField()
        Spacer(modifier = Modifier.height(20.dp))
        BirthdayRegisterField()
        Spacer(modifier = Modifier.height(20.dp))
        BabyInfoRegisterWidget()
        BabyRegisterButton(
            isNotNull = true,
            text = "로그인",
            route = "mainScreen",
            navController = navController
        )
    }
}

@Composable
fun BabyNameRegisterField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("이름") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@Composable
fun BirthdayRegisterField() {
    var text by remember { mutableStateOf("생일을 선택하세요.") }
    val context = LocalContext.current // Composable 함수 내부에서 사용

    TextButton(onClick = {
        showDatePicker(context) { year, month, dayOfMonth ->
            // 사용자가 날짜를 선택하면 텍스트 업데이트
            text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
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
fun BabyInfoRegisterWidget() {
    var heightText by remember { mutableStateOf("") }
    var weightText by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = heightText,
            onValueChange = { height ->
                if (height.all { it.isDigit() || it == '.' } && height.count { it == '.' } <= 1) {
                    heightText = height
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
                    weightText = weight
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
fun BabyRegisterButton(isNotNull: Boolean, text: String, route: String, navController: NavController) {

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
//                viewModel.registerUser(user)
                navController.navigate(route)
            },
            enabled = isNotNull
        ) {
            Text(text)
        }
    }
}