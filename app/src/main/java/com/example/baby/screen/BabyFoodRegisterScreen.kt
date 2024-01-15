package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.viewModel.LoadingViewModel

@Composable
fun BabyFoodRegisterScreen(viewModel: LoadingViewModel, navController: NavController) {
    val navigateToMainScreen by viewModel.navigateToMainScreen.observeAsState()
    Scaffold(bottomBar = { CustomBottomNavigation(navController = navController) }) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("이유식 등록 페이지")
        }

    }
}
