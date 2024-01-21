package com.example.baby.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.viewModel.AuthViewModel

@Composable
fun AuthScreen(viewModel: AuthViewModel, navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isAuthSuccessful by viewModel.isAuthSuccessful.observeAsState()

    LaunchedEffect(isAuthSuccessful) {
        if (isAuthSuccessful == true) {
            navController.navigate("mainScreen")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("휴대폰 번호") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.sendVerificationCode(phoneNumber)
        }) {
            Text("인증번호 전송")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = { Text("인증번호") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.verifyVerificationCode(verificationCode)
        }) {
            Text("인증 확인")
        }
    }
}