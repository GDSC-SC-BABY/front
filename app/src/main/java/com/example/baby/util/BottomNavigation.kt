package com.example.baby.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes

@Composable
fun CustomBottomNavigation(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val showDiaryDialog = remember { mutableStateOf(false) }


    if (showDialog.value) {
        FoodSelectDialog(navController = navController, onDismiss = { showDialog.value = false })
    }


    Box(
        modifier = Modifier
            .background(
                Color.Gray.copy(alpha = 0.7f),
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .padding(1.dp)
    ){

        BottomNavigation(
            backgroundColor = colorResource(id = R.color.background_main),
            modifier = Modifier
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
        ) {

            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.diary_icon), contentDescription = null) },
                selected = navController.currentDestination?.route == NavigationRoutes.GuideScreen.route || navController.currentDestination?.route == NavigationRoutes.BabyPatternScreen.route,
                onClick = {
                    showDiaryDialog.value = true
                    showDialog.value = false
                },
                selectedContentColor = colorResource(id = R.color.brand_color),
                unselectedContentColor = colorResource(id = R.color.gray1)
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.guide_icon), contentDescription = null) },
                selected = navController.currentDestination?.route == "registerScreen",
                onClick = {
                    navController.navigate(NavigationRoutes.RegisterScreen.route)
                },
                selectedContentColor = colorResource(id = R.color.brand_color),
                unselectedContentColor = colorResource(id = R.color.gray1)
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.home_icon), contentDescription = null) },
                selected = navController.currentDestination?.route == "mainScreen",
                onClick = {
                    // Handle navigation to Calendar screen
                    navController.navigate(NavigationRoutes.MainScreen.route)
                },
                selectedContentColor = colorResource(id = R.color.brand_color),
                unselectedContentColor = colorResource(id = R.color.gray1)
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.babyfood_icon), contentDescription = null) },
                selected = navController.currentDestination?.route == NavigationRoutes.FoodRegisterScreen.route || navController.currentDestination?.route == NavigationRoutes.SnackRegisterScreen.route,
                onClick = {
                    showDialog.value = true;
                    showDiaryDialog.value = false;
                },
                selectedContentColor = colorResource(id = R.color.brand_color),
                unselectedContentColor = colorResource(id = R.color.gray1)
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.mypage_icon), contentDescription = null) },
                selected = navController.currentDestination?.route == "myPageScreen",
                onClick = {
                    navController.navigate(NavigationRoutes.MyPageScreen.route)
                },
                selectedContentColor = colorResource(id = R.color.brand_color),
                unselectedContentColor = colorResource(id = R.color.gray1)
            )
        }
    }

    if (showDiaryDialog.value) {
        DiarySelectDialog(navController = navController, onDismiss = { showDiaryDialog.value = false })
    }

    if (showDialog.value) {
        FoodSelectDialog(navController = navController, onDismiss = { showDialog.value = false })
    }
}