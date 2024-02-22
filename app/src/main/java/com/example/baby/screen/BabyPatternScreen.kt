package com.example.baby.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.Activity
import com.example.baby.data.NavigationRoutes
import com.example.baby.network.Resource
import com.example.baby.ui.theme.MainFontStyle
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.viewModel.BabyPatternViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyPatternPage(viewModel: BabyPatternViewModel, navController: NavController, babyId: Int) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedMonth = selectedDate.monthValue
    val selectedDay = selectedDate.dayOfMonth

    LaunchedEffect(Unit) {
        viewModel.getBabyPatternWithDate(babyId, selectedDate)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "생활 패턴 모아 보기",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(NavigationRoutes.MainScreen.route){
                            popUpTo(NavigationRoutes.MainScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.sub_color))
            )
        },
        bottomBar = {
            selectBabyPattern { selectedIndex ->
                if (TabType.values()[selectedIndex] == TabType.BabyFood) {
                    navController.navigate(NavigationRoutes.FoodRegisterScreen.route)
                } else {
                    navController.navigate(
                        route = "${NavigationRoutes.BabyPatternRecordScreen.route}/$selectedIndex"
                    )
                }
            }
        }
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 30.dp),
            ) {
                Text("오늘의 육아 TIP:", style = MainFontStyle.tipText, color = Color(R.color.gray6))
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "오늘의 육아 TIP 내용은 이거지롱~",
                    style = MainFontStyle.body1,
                    fontSize = 17.sp,
                    color = Color(R.color.gray6),
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(fraction = 1f)
                    .background(color = colorResource(id = R.color.background_main)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    selectedDate = selectedDate.minusDays(1)
                    viewModel.getBabyPatternWithDate(
                        babyId,
                        selectedDate
                    )
                }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "전날",
                        tint = colorResource(id = R.color.secondary_color)
                    )
                }
                Text(
                    "${selectedMonth}월 ${selectedDay}일",
                    modifier = Modifier.padding(start = 30.dp),
                    style = StartFontStyle.startButton,
                    color = colorResource(id = R.color.secondary_color)
                )
                IconButton(onClick = {
                    selectedDate = selectedDate.plusDays(1)
                    viewModel.getBabyPatternWithDate(babyId, selectedDate)

                }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_forward),
                        contentDescription = "다음날",
                        tint = colorResource(id = R.color.secondary_color)
                    )
                }
            }
            BabyPatternCardList(viewModel)
        }
    }
}

@Composable
fun BabyPatternCardList(viewModel: BabyPatternViewModel) {
    val patternDataState by viewModel.patternDataState.collectAsState()


    when (val state = patternDataState) {
        is Resource.Loading -> {
        }
        is Resource.Success -> {
            val patternData = state.data
            Log.d("patternData", state.data.toString())
            if (patternData != null && patternData.isNotEmpty()) {
                LazyColumn {
                    items(patternData) { activity ->
                        BabyPatternCard(activity)
                    }
                }
            } else {
                Spacer(modifier= Modifier.height(20.dp))
                Text(
                    "생활 패턴을 입력해주세요",
                    style = StartFontStyle.startBody1,
                    color = Color(R.color.gray6),
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    textAlign = TextAlign.Center,
                )
            }

        }
        is Resource.Error -> {
            Text(
                "생활 패턴을 입력해주세요",
                style = StartFontStyle.startBody1,
                color = Color(R.color.gray6),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun BabyPatternCard(activity: Activity) {

    var tabType: TabType = TabType.fromTitle(activity.activityType) ?: TabType.Sleep
    Card(
        modifier = Modifier
            .fillMaxWidth()
        //.border(width = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            OutlinedButton(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(50.dp)
                    .clip(CircleShape),
                onClick = { },
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tabType.backColor,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                )
            ) {
                Icon(
                    painter = painterResource(
                        id = tabType.icon
                    ),
                    contentDescription = "Tab Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(32.dp)
//                                .padding(end = 5.dp)
                    //.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(5f)
            ) {
                Text(
                    "${tabType.title}",
                    style = MainFontStyle.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "${activity.memo}", style = MainFontStyle.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "${formatTimestampToTime(activity.startTime)}",
                    style = MainFontStyle.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "${formatTimestampToAMPM(activity.startTime)}",
                    style = MainFontStyle.body2,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

fun formatTimestampToTime(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm"))
}

fun formatTimestampToAMPM(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("a"))
}

@Composable
fun selectBabyPattern(onTabSelected: (Int) -> Unit) {

    Column {
        Divider(color = colorResource(R.color.gray4), thickness = 0.6.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabType.values().forEachIndexed { index, tabType ->

                OutlinedButton(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .wrapContentSize(),
                    onClick = { onTabSelected(index) },
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = tabType.backColor,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = tabType.icon),
                        contentDescription = "Tab Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(70.dp)
                    )
                }
            }
        }
    }
}
