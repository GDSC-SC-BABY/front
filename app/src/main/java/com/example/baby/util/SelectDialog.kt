package com.example.baby.util

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import org.threeten.bp.LocalDate

@Composable
fun FoodSelectDialog(navController: NavController, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(0.4f),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "이유식 일기",
                    color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.FoodRegisterScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp)
                )
                Text("간식 일기",
                    color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.SnackRegisterScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp))
            }
        }
    }
}

@Composable
fun DiarySelectDialog(navController: NavController, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(0.4f),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "육아 일기",
                    color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.FoodRegisterScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp)
                )
                Text("생활 패턴 기록",
                    color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.BabyPatternScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp))
            }
        }
    }
}

@Composable
fun RecordSelectDialog(navController: NavController, selectedDate: LocalDate, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(0.45f)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("이유식 일지 보기", color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.DayBabyFoodScreen.createRoute(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth))
                            Log.d("selectedDate", "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}")
                            onDismiss()
                        }
                        .padding(16.dp))
                Text("간식 일지 보기", color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.DayBabyFoodScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp))
                Text("생활 패턴 모아 보기", color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.SnackRegisterScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp))
                Text("육아 일지 보기", color = colorResource(id = R.color.gray5),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoutes.SnackRegisterScreen.route)
                            onDismiss()
                        }
                        .padding(16.dp))
            }
        }
    }
}