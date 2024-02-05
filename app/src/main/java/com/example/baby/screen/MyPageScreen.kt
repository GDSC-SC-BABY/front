package com.example.baby.screen

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.LoadingViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyPageScreen(viewModel: LoadingViewModel, navController: NavController) {
    val navigateToMainScreen by viewModel.navigateToMainScreen.observeAsState()
    Scaffold(
        bottomBar = { CustomBottomNavigation(navController = navController) }
    ) { innerPadding ->
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 40.dp,
                        bottom = innerPadding.calculateBottomPadding() + 20.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                babyInfoCard()
                Spacer(modifier = Modifier.height(20.dp))
                userInfo()
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                userCodeInfo()
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Co_parentInfo()
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(20.dp))
                DarkModeSelect()
            }
        }
    }
}

@Composable
fun babyInfoCard() {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.teddy_bear),
                contentDescription = "babyPhoto",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        SharedPreferenceUtil(context).getString("babyName", "").toString(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(
                            id = SharedPreferenceUtil(context).getInt(
                                "genderIcon",
                                R.drawable.man_icon
                            )
                        ),
                        contentDescription = "gender",
                        modifier = Modifier.size(23.dp)
                    )
                }
                Text(SharedPreferenceUtil(context).getString("birth", "").toString())
                Text("6개월")
                Text("이유식 초기")
            }

        }
    }
}

@Composable
fun userInfo() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("내 정보", fontWeight = FontWeight.Bold, fontSize = 23.sp)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "updateUserIcon")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text("닉네임", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                Text("관계", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                Text("거주지", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
            }
            Spacer(Modifier.width(45.dp))
            Column() {
                Text("통통맘")
                Text("엄마")
                Text("시흥시")
            }
        }
    }
}

@Composable
fun userCodeInfo() {
    Column {
        Text("고유 코드", fontWeight = FontWeight.Bold, fontSize = 23.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "3v8duaod88u7")
    }
}

@Composable
fun Co_parentInfo() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("공동양육자", fontWeight = FontWeight.Bold, fontSize = 23.sp)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "updateUserIcon")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text("아빠", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                Text("시터", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
            }
            Spacer(Modifier.width(45.dp))
            Column() {
                Text("통통짱사랑대디")
                Text("간지작살노련시터")
            }
        }
    }
}

@Composable
fun DarkModeSelect() {
    var isChecked by remember { mutableStateOf(false) }
    Column {
        Text("고유 코드", fontWeight = FontWeight.Bold, fontSize = 23.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "다크모드")
            Switch(checked = isChecked, onCheckedChange = {
                isChecked = !isChecked
            }
            )
        }
    }
}