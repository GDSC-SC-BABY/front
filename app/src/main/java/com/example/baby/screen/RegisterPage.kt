package com.example.baby.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.baby.viewModel.LoadingViewModel

@Composable
fun RegisterPage(viewModel: LoadingViewModel, navController: NavController) {
    val navigateToMainScreen by viewModel.navigateToMainScreen.observeAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("회원가입", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        NicknameRegisterField()
        Spacer(modifier = Modifier.height(20.dp))
        RelationshipRegisterFiled()
        RegisterButton(navController = navController)
    }
}

@Composable
fun NicknameRegisterField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("닉네임") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RelationshipRegisterFiled() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("엄마", "아빠", "베이비시터")
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedText = items[selectedIndex]

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)) {
        Text(
            text = "아기와의 관계",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
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
                value = selectedText,
                onValueChange = { },
                readOnly = true, // This makes the TextField not editable
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
                modifier = Modifier.fillMaxWidth(),
                properties = PopupProperties(focusable = false)
            ) {
                items.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
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
fun RegisterButton(navController : NavController){
    Box(
        contentAlignment = Alignment.BottomEnd
    ){
    Button(onClick = {
        navController.navigate("mainScreen")
    },
        ){
        Text("가입하기")
    }
    }
}