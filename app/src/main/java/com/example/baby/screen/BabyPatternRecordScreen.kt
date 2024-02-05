package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import com.example.baby.viewModel.LoadingViewModel

@Composable
fun BabyPatternRecordPage(viewModel: LoadingViewModel, navController: NavController) {
    // 현재 선택된 탭의 인덱스를 저장하는 상태 변수
    var selectedTab by remember { mutableStateOf(TabType.Pee) }

    Scaffold(
        topBar = {
            CustomTabRow(selectedTabIndex = selectedTab.ordinal) { index ->
                selectedTab = TabType.values()[index]
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                Text("제목", style = MaterialTheme.typography.h6)
                TextField(
                    value = selectedTab.title,
                    onValueChange = { /* Handle title change */ },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                WriteSignificant()
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("등록하기")
                }
            }
        }
    )
}

@Composable
fun CustomTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val iconList = listOf(
        R.drawable.pee_icon,
        R.drawable.meal_icon,
        R.drawable.medicine_icon,
        R.drawable.sleep_icon,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconList.forEachIndexed { index, drawable ->
            val isSelected = index == selectedTabIndex

            IconButton(
                onClick = { onTabSelected(index) }
            ) {
                Icon(
                    painter = painterResource(id = drawable),
                    contentDescription = "Tab Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(if (isSelected) 60.dp else 40.dp)
                )
            }
        }
    }
}

enum class TabType(val title: String, val content: String) {
    Pee("기저귀", "상세 내용을 작성해주세요."),
    Food("이유식", "상세 내용을 작성해주세요."),
    Medicine("복약", "상세 내용을 작성해주세요."),
    Sleep("수면", "상세 내용을 작성해주세요.")
}
