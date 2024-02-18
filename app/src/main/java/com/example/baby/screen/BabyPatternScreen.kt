package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.Activity
import com.example.baby.data.NavigationRoutes
import com.example.baby.network.Resource
import com.example.baby.viewModel.BabyPatternViewModel
import java.time.LocalDate


@Composable
fun BabyPatternPage(viewModel: BabyPatternViewModel, navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedMonth = selectedDate.monthValue
    val selectedDay = selectedDate.dayOfMonth

    Scaffold(
        content = { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {
                Text(
                    "우리 아이 생활 패턴",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 30.dp),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    "우리 아이 생활 패턴 팁!!",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 30.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 10.dp)
                        .fillMaxWidth(fraction = 1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        selectedDate = selectedDate.minusDays(1)
                        viewModel.getBabyPatternWithDate(selectedDate)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "전날")
                    }
                    Text(
                        "${selectedMonth}월 ${selectedDay}일",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    IconButton(onClick = {
                        selectedDate = selectedDate.plusDays(1)
                        viewModel.getBabyPatternWithDate(selectedDate)

                    }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "다음날")
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                BabyPatternCardList(viewModel)
            }
        },
        bottomBar = {
            selectBabyPattern { selectedIndex ->
                navController.navigate(
                    route = "${NavigationRoutes.BabyPatternRecordScreen.route}/$selectedIndex"
                )
            }
        }
    )
}

@Composable
fun BabyPatternCardList(viewModel: BabyPatternViewModel) {
    val babyPatternData = viewModel.patternDataState.collectAsState().value

    when (babyPatternData) {
        is Resource.Loading -> {
            // 로딩 상태일 때 UI 표시
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            // 성공적으로 데이터를 받아온 경우 UI 표시
            val patternData = babyPatternData.data
            // patternData를 사용하여 UI를 구성하는 코드 작성
        }
        is Resource.Error -> {
            // 오류 발생 시 UI 표시
            val errorMessage = babyPatternData.message ?: "An error occurred"
            // errorMessage를 사용하여 오류 메시지를 표시하는 UI 작성
        }
    }
}

@Composable
fun BabyPatternCard(activity: Activity){
    Card(
        modifier = Modifier
            .fillMaxWidth()
        //.padding(start = 20.dp, end = 20.dp)
        //.border(width = 0.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.meal_icon),
                    contentDescription = "Tab Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text("감기약", style = MaterialTheme.typography.h6)
                    Text("오늘은 상태가 어쩌구", style = MaterialTheme.typography.body2)
                }
            }
            Text("04:42 PM", style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun selectBabyPattern(onTabSelected: (Int) -> Unit) {
    val iconList = listOf(
        R.drawable.pee_icon,
        R.drawable.meal_icon,
        R.drawable.medicine_icon,
        R.drawable.sleep_icon,
    )

    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconList.forEachIndexed { index, drawable ->

                IconButton(
                    onClick = { onTabSelected(index) }
                ) {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = "Tab Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(45.dp)
                            .padding(end = 5.dp)
                    )
                }
            }
        }
    }
}
