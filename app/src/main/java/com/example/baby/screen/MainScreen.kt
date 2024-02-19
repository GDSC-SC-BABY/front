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
        floatingActionButton = {
            ExpandableFloatingActionButton(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.End, // 버튼의 위치 조정
        isFloatingActionButtonDocked = false, // 하단바에 도킹할지 여부
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.sub_color))
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

@Composable
fun ExpandableFloatingActionButton(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 첫 번째 추가 버튼
        AnimatedVisibility(visible = expanded) {
            FloatingActionButton(
                onClick = {
                    // 첫 번째 버튼 클릭 시 실행할 액션
                },
            ) {
                Text("육아 기록 추가")
            }
        }

        // 두 번째 추가 버튼
        AnimatedVisibility(visible = expanded) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationRoutes.BabyPatternRecordScreen.route)
                },
            ) {
                Text("생활 패턴 추가")
            }
        }

        // 메인 플로팅 액션 버튼
        FloatingActionButton(
            onClick = {
                expanded = !expanded // 확장 상태 토글
            },
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = "메인 버튼"
            )
        }
    }
}