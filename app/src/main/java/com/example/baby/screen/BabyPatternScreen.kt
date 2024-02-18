package com.example.baby.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.baby.data.NavigationRoutes
import com.example.baby.viewModel.BabyPatternViewModel


@Composable
fun BabyPatternPage(viewModel: BabyPatternViewModel, navController: NavController) {
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
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "전날")
                    }
                    Text(
                        "10월 10일",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "다음날")
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                BabyPatternCard()
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
fun BabyPatternCard() {
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
            Text("04:42PM", style = MaterialTheme.typography.h6)
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
