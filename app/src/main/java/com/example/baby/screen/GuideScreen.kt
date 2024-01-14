package com.example.baby.screen

import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.baby.viewModel.LoadingViewModel
import com.example.baby.viewModel.TodoViewModel
import java.util.Timer
import java.util.logging.Handler

@Composable
fun GuideScreen(viewModel: LoadingViewModel, navController: NavController) {
    val navigateToMainScreen by viewModel.navigateToMainScreen.observeAsState()
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
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text("Guide")
        }
    }
}