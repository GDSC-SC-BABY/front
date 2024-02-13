package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baby.R


@Composable
fun BabyPatternPage() {
    Scaffold(
        content = { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding()
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
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    "10월 10일",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 30.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                BabyPatternCard()
            }
        },
        bottomBar = { selectBabyPattern(selectedTabIndex = 1) {} }
    )
}

@Composable
fun BabyPatternCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ){
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
fun selectBabyPattern(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
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
                        modifier = Modifier.size(45.dp)
                            .padding(end = 5.dp)
                    )
                }
            }
        }
    }
}
