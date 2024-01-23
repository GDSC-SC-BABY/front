package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.viewModel.CalendarViewModel

@Composable
fun MainScreen(
    viewModel: CalendarViewModel,
    navController: NavController
) {
    Scaffold(
        bottomBar = { CustomBottomNavigation(navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.White)
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

