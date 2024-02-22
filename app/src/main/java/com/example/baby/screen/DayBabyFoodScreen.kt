package com.example.baby.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.baby.R
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyFoodInfo
import com.example.baby.data.NavigationRoutes
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.viewModel.BabyFoodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayBabyFoodScreen(
    viewModel: BabyFoodViewModel,
    navController: NavController,
    year: Int,
    month: Int,
    day: Int
) {
    val babyId = 1
    var babyFoodResponse by remember { mutableStateOf<BabyFoodAllResponse?>(null) }

    LaunchedEffect(babyId) {
        babyFoodResponse = viewModel.getAllBabyFoodByBabyId(babyId)
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(color = colorResource(id = R.color.sub_color)),
                title = {
                    Text(
                        "이유식 일지",
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.secondary_color),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },

                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.previous_month_icon),
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
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
                SelectDayWidget(year, month, day)
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    babyFoodResponse?.babyFoodGetResList?.let { babyFoods ->
                        items(babyFoods.size) { index ->
                            BabyFoodCard(babyFood = babyFoods[index], navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectDayWidget(year: Int, month: Int, day: Int) {
    var selectedDate by remember { mutableStateOf(LocalDate.of(year, month, day)) }
    val formatter = DateTimeFormatter.ofPattern("MM월 dd일")

    Row(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 10.dp)
            .fillMaxWidth(fraction = 1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            selectedDate = selectedDate.minusDays(1)
//            viewModel.getBabyPatternWithDate(selectedDate)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.previous_day),
                contentDescription = "전날",
                tint = colorResource(
                    id = R.color.secondary_light
                )
            )
        }
        Text(
            formatter.format(selectedDate),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 30.dp)
        )
        IconButton(onClick = {
            selectedDate = selectedDate.plusDays(1)
//            viewModel.getBabyPatternWithDate(selectedDate)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.next_day),
                contentDescription = "다음날",
                tint = colorResource(
                    id = R.color.secondary_light
                )
            )
        }
    }
}

@Composable
fun BabyFoodCard(babyFood: BabyFoodInfo, navController: NavController) {
    val context = LocalContext.current

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedTime = babyFood.dateTime.format(formatter)

    Log.d("babyFood", babyFood.imageUrl)

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
                    painter = rememberAsyncImagePainter("https://storage.googleapis.com/gdsc_solution_challenge/f0ad6597-c797-41bf-8fba-d9b283110813"),
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
                        Text(
                            formattedTime,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.secondary_light),
                            fontSize = 17.sp
                        )
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
                        babyFood.baseMeal,
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