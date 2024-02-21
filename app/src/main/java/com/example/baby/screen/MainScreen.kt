package com.example.baby.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.viewModel.CalendarViewModel

@Composable
fun MainScreen(
    viewModel: CalendarViewModel,
    navController: NavController
) {
    Scaffold(
        bottomBar = { CustomBottomNavigation(navController = navController) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
//            val calendarState = remember { CalendarState() }

//            ExpandableCalendar(
//                state = calendarState,
//                onDayClicked = { date ->
//                    // 날짜 클릭 시 액션 처리
//                }
//            )
            CustomCalendarView(viewModel = viewModel, navController = navController)
        }
    }
}