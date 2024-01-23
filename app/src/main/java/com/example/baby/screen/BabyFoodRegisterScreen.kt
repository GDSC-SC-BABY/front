package com.example.baby.screen

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.baseMealList
import com.example.baby.util.mealTimeList
import com.example.baby.viewModel.BabyFoodRegisterViewModel
import com.example.baby.viewModel.DateViewModel

@Composable
fun BabyFoodRegisterScreen(
    viewModel: DateViewModel,
    babyFoodViewModel: BabyFoodRegisterViewModel,
    navController: NavController
) {
    Scaffold(
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
fun BabyFoodRegisterInfo(viewModel: DateViewModel, foodViewModel: BabyFoodRegisterViewModel) {
    var text by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .size(100.dp)
        ) {
            ImagePickerBox(viewModel = foodViewModel)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(viewModel.getDateNow(), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(5.dp))
            MealTimeSelectDropDownMenu(foodViewModel)
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { gram ->
                    if (gram.all { it.isDigit() }) {
                        text = gram
                    }
                },
                label = { Text("베이스 죽 (g)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(horizontal = 30.dp)
            )
        }
    }
}

@Composable
fun ImagePickerBox(viewModel: BabyFoodRegisterViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onImagePicked(uri)
    }

    Box(
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .size(100.dp)
            .clickable {
                launcher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Image")
    }

    val selectedImageUri by viewModel.selectedImage.observeAsState()
    selectedImageUri?.let { uri ->
        Image(painter = rememberAsyncImagePainter(uri), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealTimeSelectDropDownMenu(viewModel: BabyFoodRegisterViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val items = mealTimeList
    val relationship by viewModel.mealTime.collectAsState()
    val selectedIndex = items.indexOf(relationship)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = items.getOrElse(selectedIndex) { "00시" },
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown arrow",
                        Modifier.clickable { expanded = true }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledIndicatorColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(0.2f),
                properties = PopupProperties(focusable = false)
            ) {
                items.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.mealTime.value = items[index]
                            expanded = false
                        }
                    ) {
                        Text(text = text)
                    }
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
        Text(text = "베이스 죽", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
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
    val borderColor = if (isSelected) Color.Cyan else Color.Transparent

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 100.dp, height = 30.dp),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Text(item, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ToppingSelectWidget(viewModel: BabyFoodRegisterViewModel) {
    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "토핑", fontWeight = FontWeight.SemiBold)

            // 토핑 추가 버튼
            Button(onClick = { viewModel.addTopping() }) {
                Text(text = "+")
            }

        }

        // 동적으로 생성된 토핑 필드들
        viewModel.toppings.forEachIndexed { index, topping ->
            ToppingField(index, topping, viewModel)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ToppingField(index: Int, topping: String, viewModel: BabyFoodRegisterViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(0.6f),
            value = topping,
            onValueChange = { updatedTopping -> viewModel.updateTopping(index, updatedTopping) },
            label = { Text("재료") }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            value = topping,
            onValueChange = { updatedTopping -> viewModel.updateTopping(index, updatedTopping) },
            label = { Text("용량 (g)") }
        )
    }
}

@Composable
fun WriteSignificant() {
    var text by remember { mutableStateOf("") }
    Column {
        Text(text = "특이사항", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    Color.LightGray,
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
                    Text("이유식을 먹일 때 특이사항이 있었나요?", fontSize = 14.sp)
                },
                textStyle = TextStyle(fontSize = 14.sp),
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