package com.example.baby.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import com.example.baby.data.User
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.ui.theme.nanumSquare

@Composable
fun MemberTypeScreen(navController: NavController, context: Context) {
    var appName = context.resources.getString(R.string.app_name)

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("${appName}에 어서오세요.", style = StartFontStyle.startBody1, color = Color(R.color.gray4))
        Spacer(modifier = Modifier.height(12.dp))
        Text("${appName}에서", style = StartFontStyle.startSubtitle)
        Spacer(modifier = Modifier.height(12.dp))
        Text("어떤 기능을 사용하고 싶으신가요?", style = StartFontStyle.startHeadline)
        Image(
            painter = painterResource(id = R.drawable.start_2),
            contentDescription = "start_2",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        selectParentingInfoButton {
            navController.navigate(route = NavigationRoutes.RegisterScreen.route)
        }
        Spacer(modifier = Modifier.height(10.dp))
        selectParentingMemoButton {
            navController.navigate(route = NavigationRoutes.RegisterScreen.route)
        }
    }
}

@Composable
fun selectParentingInfoButton(
    onTabSelected: () -> Unit
) {
    Button(
        onClick = onTabSelected,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.background_gray),
            contentColor = colorResource(id = R.color.secondary_color),
        ),
        elevation = ButtonDefaults.elevation(0.dp,0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 13.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "부모가 되기 위해 알아야 할",
                    style = TextStyle(
                        fontFamily = nanumSquare,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = colorResource(id = R.color.secondary_light)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    "육아에 대한 다양한 정보 수집",
                    style = StartFontStyle.startButton,
                    color = colorResource(id = R.color.secondary_color)
                )
            }
            IconButton(onClick = onTabSelected) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "",
                    tint = colorResource(id = R.color.secondary_light)
                )
            }
        }
    }
}

@Composable
fun selectParentingMemoButton(
    onTabSelected: () -> Unit
) {
    Button(
        onClick = onTabSelected,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.background_main),
            contentColor = colorResource(id = R.color.secondary_color),
        ),
        elevation = ButtonDefaults.elevation(0.dp,0.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = colorResource(id = R.color.background_main))
                .padding(vertical = 13.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "아이를 기르면서 꾸준히 기록할 수 있는",
                    style = TextStyle(
                        fontFamily = nanumSquare,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = colorResource(id = R.color.secondary_color)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    "육아 일지 작성하기 기능",
                    style = StartFontStyle.startButton,
                    color = colorResource(id = R.color.secondary_light)
                )
            }
            IconButton(onClick = onTabSelected) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "",
                    tint = colorResource(id = R.color.secondary_color)
                )
            }
        }
    }
}