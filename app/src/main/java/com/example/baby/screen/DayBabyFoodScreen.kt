package com.example.baby.screen

import android.util.Log
import android.widget.ListView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import com.example.baby.data.UserResponse
import com.example.baby.network.Resource
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.BabyRegisterViewModel
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.UserRegisterViewModel

@Composable
fun DayBabyFoodScreen(
    viewModel: DateViewModel,
    userViewModel: UserRegisterViewModel,
    navController: NavController
) {

    LaunchedEffect(true) {
        userViewModel.getUserInfo("ztg4NhVNvgXpWMhxw3bx7k3p4SC2")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.sub_color),
                elevation = 0.dp,
                title = {
                    Text(
                        "이유식 일지",
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.secondary_color),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
        bottomBar = { CustomBottomNavigation(navController = navController) }
    ) { innerPadding ->
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding() + 10.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    viewModel.getDateNow(),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.secondary_color),
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) {
                        BabyFoodCard(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BabyFoodCard(navController: NavController) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(NavigationRoutes.FoodDetailScreen.route)
            },
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.6f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "babyFoodPhoto",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(
                            colorResource(id = R.color.background_gray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(7.dp)
                    ) {
                        Text(
                            "07",
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.secondary_light),
                            fontSize = 17.sp
                        )
                        Text(
                            "시",
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.gray5),
                            fontSize = 17.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(
                            colorResource(id = R.color.background_gray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "당근죽",
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.secondary_light),
                        fontSize = 17.sp,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
        }
    }
}