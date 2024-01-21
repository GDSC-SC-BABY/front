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
import com.example.baby.viewModel.UserRegisterViewModel

@Composable
fun UserRegisterPage(viewModel: UserRegisterViewModel, navController: NavController) {

    val isFormValid by viewModel.isFormValid.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("회원가입", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        NicknameRegisterField(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        RelationshipRegisterFiled(viewModel)
        RegisterButton(
            isNotNull = isFormValid,
            text = "가입하기",
            route = "babyRegisterScreen",
            navController = navController
        )
    }
}

@Composable
fun NicknameRegisterField(viewModel: UserRegisterViewModel) {

    val nickname by viewModel.nickname.collectAsState()

    OutlinedTextField(
        value = nickname,
        onValueChange = { updatedNickname ->
            viewModel.nickname.value = updatedNickname
        },
        label = { Text("닉네임") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RelationshipRegisterFiled(viewModel: UserRegisterViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("엄마", "아빠", "베이비시터")
    val relationship by viewModel.relationship.collectAsState() // StateFlow를 State로 변환
    val selectedIndex = items.indexOf(relationship) // 현재 relationship을 기반으로 selectedIndex를 결정


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
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
                value = items.getOrElse(selectedIndex) { "엄마" },
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
                            viewModel.relationship.value = items[index]
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
fun RegisterButton(isNotNull: Boolean, text: String, route: String, navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                navController.navigate(route)
            },
            enabled = isNotNull
        ) {
            Text(text)
        }
    }
}