package com.example.baby.util

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.baby.util.FoodSelectDialog

@Composable
fun CustomBottomNavigation(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }


    if (showDialog.value) {
        FoodSelectDialog(navController = navController, onDismiss = { showDialog.value = false })
    }


    androidx.compose.material.BottomNavigation(
        modifier = Modifier.wrapContentHeight()
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
            selected = navController.currentDestination?.route == "mainScreen",
            onClick = {
                // Handle navigation to Calendar screen
                navController.navigate("mainScreen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = null) },
            selected = navController.currentDestination?.route == "guideScreen",
            onClick = {
                // Handle navigation to Guide screen
                navController.navigate("GuideScreen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            selected = navController.currentDestination?.route == "policyScreen",
            onClick = {
                navController.navigate("PolicyScreen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            selected = navController.currentDestination?.route == "foodRegisterScreen" || navController.currentDestination?.route == "TreatRegisterScreen",
            onClick = {
                showDialog.value = true;
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            selected = navController.currentDestination?.route == "myPageScreen",
            onClick = {
                navController.navigate("myPageScreen")
            }
        )
    }
    if (showDialog.value) {
        FoodSelectDialog(navController = navController, onDismiss = { showDialog.value = false })
    }
}