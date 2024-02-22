package com.example.baby.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.BabyFood
import com.example.baby.data.NavigationRoutes
import com.example.baby.data.Snack
import com.example.baby.data.Topping
import com.example.baby.network.Resource
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.util.mealTimeList
import com.example.baby.viewModel.BabyFoodViewModel
import com.example.baby.viewModel.BabySnackRegisterViewModel
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.ImageUploadViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BabySnackRegisterScreen(
    viewModel: ImageUploadViewModel,
    dateViewModel: DateViewModel,
    babySnackViewModel: BabySnackRegisterViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.sub_color),
                elevation = 0.dp,
                title = {
                    Text(
                        "이유식 기록",
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.secondary_color),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
        bottomBar = {
            AddSnackButton(
                viewModel = babySnackViewModel,
                dateViewModel = dateViewModel,
                imageViewModel = viewModel,
                navController = navController
            )
        }
    ) { innerPadding ->
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding() + 10.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                BabySnackRegisterInfo(viewModel = viewModel, foodViewModel = babySnackViewModel)
                Spacer(modifier = Modifier.height(25.dp))
                SnackSelectWidget(viewModel = babySnackViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                WriteSnackSignificant(babySnackViewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun BabySnackRegisterInfo(
    viewModel: ImageUploadViewModel,
    foodViewModel: BabySnackRegisterViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ImagePickerBox(viewModel = viewModel)
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SnackTimeSelectDropDownMenu(foodViewModel, Modifier.weight(1f))
            Spacer(Modifier.width(10.dp))
            RegisterSnackAmount(foodViewModel, Modifier.weight(1f))
        }
    }
}

@Composable
fun RegisterSnackAmount(viewModel: BabySnackRegisterViewModel, modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { gram ->
            if (gram.all { it.isDigit() }) {
                text = gram
                viewModel.setSnackAmount(gram)
            }
        },
        singleLine = true,
        placeholder = { Text("간식 용량 작성 (g)", fontSize = 14.sp, textAlign = TextAlign.Center) },
        modifier = modifier
            .height(50.dp)
            .background(colorResource(R.color.background_main), RoundedCornerShape(12.dp)),

        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.secondary_color),
            backgroundColor = colorResource(R.color.background_main),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            placeholderColor = colorResource(R.color.gray3)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SnackTimeSelectDropDownMenu(viewModel: BabySnackRegisterViewModel, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val items = mealTimeList
    val time by viewModel.snackTime.collectAsState()
    val selectedIndex = items.indexOf(time)


    Column(
        modifier = modifier
            .height(50.dp)
            .background(colorResource(R.color.background_main), RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (selectedIndex != -1) items[selectedIndex] else "시간 선택",
                modifier = Modifier
                    .background(Color.Transparent)
                    .weight(1f)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.secondary_color)
            )
            IconButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.weight(0.3f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dropdown_icon),
                    contentDescription = "dropdown icon",
                    tint = colorResource(R.color.secondary_color)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.42f)
                .fillMaxHeight(0.2f)
                .background(Color.White, RoundedCornerShape(12.dp)),
            properties = PopupProperties(focusable = false)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.setSnackTime(text)
                        expanded = false
                    }
                ) {
                    Text(text = text)
                }
            }
        }
    }
}

@Composable
fun SnackSelectWidget(viewModel: BabySnackRegisterViewModel) {
    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "어떤 간식인가요?",
                color = colorResource(id = R.color.secondary_light),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(onClick = {
                viewModel.addSnack()
                viewModel.addSnackAmounts()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "updateUserIcon")
            }
        }

        viewModel.snacks.forEachIndexed { index, snack ->
            SnackField(index, snack, viewModel)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun SnackField(index: Int, snack: String, viewModel: BabySnackRegisterViewModel) {
    var amount by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = snack,
            onValueChange = { updatedTopping ->
                viewModel.updateSnack(index, updatedTopping)
            },
            singleLine = true,
            placeholder = { Text("간식 이름", fontSize = 14.sp, textAlign = TextAlign.Center) },
            modifier = Modifier
                .height(50.dp)
                .weight(0.6f)
                .padding(horizontal = 15.dp)
                .background(colorResource(R.color.background_main), RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.secondary_color),
                backgroundColor = colorResource(R.color.background_gray),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                placeholderColor = colorResource(R.color.gray3)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        TextField(
            value = amount,
            onValueChange = { gram ->
                if (gram.all { it.isDigit() }) {
                    amount = gram
                    viewModel.updateSnackAmounts(index, gram.toInt())
                }
            },
            singleLine = true,
            placeholder = { Text("용량", fontSize = 14.sp, textAlign = TextAlign.Center) },
            modifier = Modifier
                .height(50.dp)
                .weight(0.4f)
                .padding(horizontal = 15.dp)
                .background(colorResource(R.color.background_main), RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.secondary_color),
                backgroundColor = colorResource(R.color.background_gray),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                placeholderColor = colorResource(R.color.gray3)
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun WriteSnackSignificant(viewModel: BabySnackRegisterViewModel) {
    val text by viewModel.significant.collectAsState()
    Column {
        Text(
            text = "특이사항에 대해 적어요",
            color = colorResource(id = R.color.secondary_light),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.background_main),
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxWidth()
                .height(300.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    viewModel.setSignificant(newText)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "특이사항이 있었나요?",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.secondary_light)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.secondary_color)
                ),
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

@Composable
fun AddSnackButton(
    viewModel: BabySnackRegisterViewModel,
    dateViewModel: DateViewModel,
    imageViewModel: ImageUploadViewModel,
    navController: NavController
) {
    val state = viewModel.snackRegisterState.collectAsState().value
    val amount = viewModel.amount.collectAsState().value
    val url ="https://tuk-planet.s3.ap-northeast-2.amazonaws.com/images/1b066b61-f69a-45c1-830a-67f953c92437-%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-10-17%20195842.png"
        // imageViewModel.imageUrl.observeAsState().value
    val note = viewModel.significant.collectAsState().value
    val snackList = viewModel.snacks.zip(viewModel.snackAmounts) { snack, amount ->
        Topping(name = snack, amount = amount)
    }

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp),
    ) {
        Button(
            onClick = {
                val dateStr = dateViewModel.getDateNow() + " " + viewModel.snackTime.value + " 33분"

                val realDate = dateViewModel.parseStringToLocalDateTime(dateStr)

                url?.let {
                    Snack(
                        babyId = 38,
//                        SharedPreferenceUtil(context).getString("babyId", "").toString(),
                        dateTime = realDate!!,
                        amount = amount.toInt(),
                        url = it,
                        note = note,
                        toppingList = snackList
                    )
                }?.let { viewModel.registerBabyFood(it) }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = colorResource(id = R.color.secondary_color),
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(36.dp)
        ) {
            Text("저장하기", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is Resource.Success -> {
                navController.navigate(NavigationRoutes.MainScreen.route)
            }

            is Resource.Error -> {
                // 오류가 발생한 경우 로그 출력
                Log.d("RegisterButton", "API 오류: ${state.message}")
            }

            is Resource.Loading -> {
                // 필요한 경우 로딩 상태 처리
            }
        }
    }
}