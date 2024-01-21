package com.example.baby.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FoodSelectDialog(navController: NavController, onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("이유식 기록", modifier = Modifier.clickable {
                    navController.navigate("foodRegisterScreen")
                    onDismiss()
                }.padding(16.dp))
                Divider()
                Text("간식 기록", modifier = Modifier.clickable {
                    navController.navigate("foodRegisterScreen")
                    onDismiss()
                }.padding(16.dp))
            }
        }
    }
}