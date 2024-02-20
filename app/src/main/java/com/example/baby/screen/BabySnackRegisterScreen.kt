package com.example.baby.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.mealTimeList
import com.example.baby.viewModel.BabyFoodViewModel
import com.example.baby.viewModel.BabySnackRegisterViewModel
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.ImageUploadViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BabySnackRegisterScreen(
    viewModel: ImageUploadViewModel,
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
            AddSnackButton(navController = navController)
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
//                    BaseMealSelectWidget(height = height)
//                    Spacer(modifier = Modifier.height(10.dp))
                SnackSelectWidget(viewModel = babySnackViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                DrinkSelectWidget(viewModel = babySnackViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                WriteSnackSignificant(babySnackViewModel)
                Spacer(modifier = Modifier.height(10.dp))
//                AddMealButton(navController)
            }
        }
    }
}

@Composable
fun BabySnackRegisterInfo(
    viewModel: ImageUploadViewModel,
    foodViewModel: BabySnackRegisterViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .weight(0.8f)
                .height(100.dp)
        ) {
            ImagePickerBox(viewModel = viewModel)
        }
//        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            SnackTimeSelectDropDownMenu(foodViewModel)
            Spacer(Modifier.height(10.dp))
            RegisterSnackAmount(foodViewModel)
        }
    }
}

@Composable
fun RegisterSnackAmount(viewModel: BabySnackRegisterViewModel) {
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
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 15.dp)
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

//@Composable
//fun ImagePickerBox(viewModel: BabyFoodRegisterViewModel) {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        viewModel.onImagePicked(uri)
//    }
//
//    Box(
//        modifier = Modifier
//            .background(
//                color = Color.LightGray,
//                shape = RoundedCornerShape(15.dp)
//            )
//            .size(100.dp)
//            .clickable {
//                launcher.launch("image/*")
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Image")
//    }
//
//    val selectedImageUri by viewModel.selectedImage.observeAsState()
//    selectedImageUri?.let { uri ->
//        Image(painter = rememberAsyncImagePainter(uri), contentDescription = null)
//    }
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SnackTimeSelectDropDownMenu(viewModel: BabySnackRegisterViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val items = mealTimeList
    val time by viewModel.snackTime.collectAsState()
    val selectedIndex = items.indexOf(time)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 15.dp)
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

//@Composable
//fun BaseMealSelectWidget(height: Dp) {
//    val dataList = (0..7).map { baseMealList[it] }
//    val selectedMeal = remember { mutableStateOf<String?>(null) }
//
//    Column() {
//        Text(text = "베이스 죽", fontWeight = FontWeight.SemiBold)
//        Spacer(modifier = Modifier.height(10.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(height)
//                .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
//        ) {
//            LazyVerticalGrid(
//                GridCells.Fixed(4),
//                contentPadding = PaddingValues(4.dp),
//                modifier = Modifier.align(Alignment.Center)
//            ) {
//                items(dataList) { item ->
//                    BaseMealGridItem(item, selectedMeal.value == item) {
//                        selectedMeal.value = item
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun BaseMealGridItem(item: String, isSelected: Boolean, onClick: () -> Unit) {
//    val borderColor = if (isSelected) Color.Cyan else Color.Transparent
//
//    Box(
//        modifier = Modifier
//            .padding(4.dp)
//            .size(width = 100.dp, height = 30.dp),
//        contentAlignment = Alignment.Center,
//    ) {
//        OutlinedButton(
//            onClick = onClick,
//            modifier = Modifier
//                .fillMaxSize(),
//            border = BorderStroke(1.dp, borderColor)
//        ) {
//            Text(item, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
//        }
//    }
//}

//@Composable
//fun SnackSelectWidget(viewModel: BabySnackRegisterViewModel) {
//    Column {
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "간식", fontWeight = FontWeight.SemiBold)
//
//            // 토핑 추가 버튼
//            Button(onClick = { viewModel.addSnack() }) {
//                Text(text = "+")
//            }
//
//        }
//
//        // 동적으로 생성된 토핑 필드들
//        viewModel.snacks.forEachIndexed { index, topping ->
//            SnackField(index, topping, viewModel)
//            Spacer(modifier = Modifier.height(10.dp))
//        }
//    }
//}

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
fun SnackField(index: Int, topping: String, viewModel: BabySnackRegisterViewModel) {
    var amount by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = topping,
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
fun DrinkSelectWidget(viewModel: BabySnackRegisterViewModel) {
    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "어떤 음료인가요?",
                color = colorResource(id = R.color.secondary_light),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(onClick = {
                viewModel.addDrink()
                viewModel.addDrinkAmounts()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "updateUserIcon")
            }
        }

        viewModel.drinks.forEachIndexed { index, drink ->
            DrinkField(index, drink, viewModel)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DrinkField(index: Int, drink: String, viewModel: BabySnackRegisterViewModel) {
    var amount by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = drink,
            onValueChange = { drink ->
                viewModel.updateDrink(index, drink)
            },
            singleLine = true,
            placeholder = { Text("음료 이름", fontSize = 14.sp, textAlign = TextAlign.Center) },
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
                    viewModel.updateDrinkAmounts(index, gram.toInt())
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
fun AddSnackButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp),
    ) {
        Button(
            onClick = {
                      navController.popBackStack()
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
}