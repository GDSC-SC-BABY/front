package com.example.baby.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.baby.R
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.baseMealList
import com.example.baby.util.mealTimeList
import com.example.baby.viewModel.BabyFoodViewModel
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.ImageUploadViewModel

@Composable
fun BabyFoodRegisterScreen(
    viewModel: ImageUploadViewModel,
    babyFoodViewModel: BabyFoodViewModel,
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
        bottomBar = { CustomBottomNavigation(navController = navController) }
    ) { innerPadding ->
        BoxWithConstraints {
            val height = maxHeight.times(0.13f)
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding() + 20.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                BabyFoodRegisterInfo(viewModel = viewModel, foodViewModel = babyFoodViewModel)
                Spacer(modifier = Modifier.height(25.dp))
                BaseMealSelectWidget(height = height)
                Spacer(modifier = Modifier.height(10.dp))
                ToppingSelectWidget(viewModel = babyFoodViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                WriteSignificant()
                Spacer(modifier = Modifier.height(10.dp))
                AddMealButton(navController)
            }
        }
    }
}

@Composable
fun BabyFoodRegisterInfo(viewModel: ImageUploadViewModel, foodViewModel: BabyFoodViewModel) {
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
            MealTimeSelectDropDownMenu(foodViewModel)
            Spacer(Modifier.height(10.dp))
            RegisterBaseMealAmount()
        }
    }
}

@Composable
fun RegisterBaseMealAmount() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { gram ->
            if (gram.all { it.isDigit() }) {
                text = gram
            }
        },
        singleLine = true,
        placeholder = { Text("죽 무게 작성 (g)", fontSize = 14.sp, textAlign = TextAlign.Center) },
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

@Composable
fun ImagePickerBox(viewModel: ImageUploadViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it)
        }
    }

    Box(
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { launcher.launch("image/*") }
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val imageUrl by viewModel.imageUrl.observeAsState()
        if (imageUrl == null) {
            Icon(
                painter = painterResource(id = R.drawable.image_picker_icon),
                contentDescription = "Add Image",
                tint = Color.Gray
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Uploaded Image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealTimeSelectDropDownMenu(viewModel: BabyFoodViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val items = mealTimeList
    val time by viewModel.mealTime.collectAsState()
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
                        viewModel.setMealTime(text)
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
fun BaseMealSelectWidget(height: Dp) {
    val dataList = (0..7).map { baseMealList[it] }
    val selectedMeal = remember { mutableStateOf<String?>(null) }

    Column() {
        Text(
            text = "오늘의 베이스 죽은?",
            color = colorResource(id = R.color.secondary_light),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(
                    color = colorResource(id = R.color.background_main),
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            LazyVerticalGrid(
                GridCells.Fixed(4),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                items(dataList) { item ->
                    BaseMealGridItem(item, selectedMeal.value == item) {
                        selectedMeal.value = item
                    }
                }
            }
        }
    }
}

@Composable
fun BaseMealGridItem(item: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (isSelected) colorResource(R.color.sub_color) else colorResource(id = R.color.background_gray)
    val textColor =
        if (isSelected) colorResource(R.color.gray6) else colorResource(id = R.color.gray3)

    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 110.dp, height = 40.dp)
            .clickable(onClick = onClick),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = item,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ToppingSelectWidget(viewModel: BabyFoodViewModel) {
    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "토핑은 어떤 것을 넣었나요?",
                color = colorResource(id = R.color.secondary_light),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(onClick = {
                viewModel.addTopping()
                viewModel.addToppingAmount()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "updateUserIcon")
            }
        }

        viewModel.toppings.forEachIndexed { index, topping ->
            ToppingField(index, topping, viewModel)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ToppingField(index: Int, topping: String, viewModel: BabyFoodViewModel) {
    var amount by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = topping,
            onValueChange = { updatedTopping ->
                viewModel.updateTopping(index, updatedTopping)
            },
            singleLine = true,
            placeholder = { Text("재료 이름", fontSize = 14.sp, textAlign = TextAlign.Center) },
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
                    viewModel.updateToppingAmount(index, gram.toInt())
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
fun WriteSignificant() {
    var text by remember { mutableStateOf("") }
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
                    text = newText
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
fun AddMealButton(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("등록")
        }
    }
}