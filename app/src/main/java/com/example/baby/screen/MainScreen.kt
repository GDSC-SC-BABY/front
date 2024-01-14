package com.example.baby.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.TodoViewModel
import com.example.baby.R
import com.example.baby.viewModel.CalendarViewModel

@Composable
fun MainScreen(
    viewModel: CalendarViewModel,
    navController: NavController
) {
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
                    label = { Text(text = "Calendar") },
                    selected = navController.currentDestination?.route == "mainScreen",
                    onClick = {
                        // Handle navigation to Calendar screen
                        navController.navigate("mainScreen")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.List, contentDescription = null) },
                    label = { Text(text = "Guide") },
                    selected = navController.currentDestination?.route == "GuideScreen",
                    onClick = {
                        // Handle navigation to Guide screen
                        navController.navigate("GuideScreen")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text(text = "Policy") },
                    selected = navController.currentDestination?.route == "PolicyScreen",
                    onClick = {
                        // Handle navigation to Policy screen
                        navController.navigate("PolicyScreen")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text(text = "MyPage") },
                    selected = navController.currentDestination?.route == "myPageScreen",
                    onClick = {
                        // Handle navigation to Policy screen
                        navController.navigate("myPageScreen")
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CustomCalendarView(viewModel = viewModel)
        }
    }
}

