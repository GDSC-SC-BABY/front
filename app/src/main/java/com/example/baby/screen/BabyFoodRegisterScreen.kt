package com.example.baby.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.baseMealList
import com.example.baby.viewModel.DateViewModel

@Composable
fun BabyFoodRegisterScreen(viewModel: DateViewModel, navController: NavController) {
    Scaffold(bottomBar = { CustomBottomNavigation(navController = navController) }
    )
    {
        BoxWithConstraints {
            val height = maxHeight.times(0.13f)
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            ) {
                Text(text = "통통이 초기 이유식", fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(20.dp))
                BabyFoodRegisterInfo(viewModel = viewModel)
                Spacer(modifier = Modifier.height(25.dp))
                BaseMealSelectWidget(height = height)
                Spacer(modifier = Modifier.height(10.dp))
                ToppingSelectWidget(height = height)
                Spacer(modifier = Modifier.height(10.dp))
                WriteSignificant()
            }
        }
    }
}

@Composable
fun BabyFoodRegisterInfo(viewModel: DateViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .size(100.dp)
        ) {
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(viewModel.getDateNow())
            Spacer(modifier = Modifier.height(5.dp))
            Text("16시")
            Spacer(modifier = Modifier.height(5.dp))
            Text("25g")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseMealSelectWidget(height: Dp) {
    val dataList = (0..7).map { baseMealList[it] }
    val selectedMeal = remember { mutableStateOf<String?>(null) }

    Column() {
        Text(text = "베이스 죽", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                items(dataList) { item ->
                    BaseMealGridItem(item, selectedMeal.value == item) {
                        selectedMeal.value = item
                    }
                }
            }
        }
    }
}

@Composable
fun BaseMealGridItem(item: String, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color.Cyan else Color.Transparent

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 100.dp, height = 30.dp),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Text(item, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToppingSelectWidget(height: Dp) {
    val dataList = (0..7).map { baseMealList[it] }
    val selectedMeal = remember { mutableStateOf<String?>(null) }

    Column {
        Text(text = "토핑", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                items(dataList) { item ->
                    ToppingGridItem(item, selectedMeal.value == item) {
                        selectedMeal.value = item
                    }
                }
            }
        }
    }
}

@Composable
fun ToppingGridItem(item: String, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color.Cyan else Color.Transparent

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 90.dp, height = 30.dp),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Text(item, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun WriteSignificant() {
    var text by remember { mutableStateOf("") }
    Column {
        Text(text = "특이사항", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxWidth()
                .height(150.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("이유식을 먹일 때 특이사항이 있었나요?", fontSize = 14.sp)
                },
                textStyle = TextStyle(fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = false,
                maxLines = 6,
            )
        }
    }
}