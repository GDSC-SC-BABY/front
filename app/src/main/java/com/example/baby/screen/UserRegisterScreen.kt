package com.example.baby.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.NavigationRoutes
import com.example.baby.data.User
import com.example.baby.network.Resource
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.ui.theme.nanumSquare
import com.example.baby.viewModel.UserRegisterViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRegisterScreen(
    userId: String,
    viewModel: UserRegisterViewModel,
    navController: NavController
) {

    val isFormValid by viewModel.isFormValid.collectAsState()
    val relation by viewModel.relationship.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "회원정보 입력",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
                },
                //colors = Color(R.color.white)/ㅇ//
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 20.dp, horizontal = 30.dp),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("닉네임을 정해주세요.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            NicknameRegisterField(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            Text("아이와의 관계.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            RelationshipRegisterFiled(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            Text("공동양육자와 같이 사용할 수 있어요.", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(20.dp))
            CoParentCodeRegisterField(viewModel, userId)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Q : 공동양육자 코드가 뭔가요?",
                fontSize = 13.sp,
                fontFamily = nanumSquare,
                fontWeight = FontWeight.Light
            )
            Text(
                "A : 공동양육자 코드는 계정마다 발급해드리는 고유한 코드번호로,\n 함께 앱을 사용할 사람들과 동일한 코드를 입력하시면 같은 육아일지를 공유할 수 있습니다.",
                fontSize = 13.sp,
                fontFamily = nanumSquare,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(50.dp))
            RegisterButton(
                isNotNull = isFormValid,
                viewModel = viewModel,
                user = User(userId = userId, viewModel.nickname.value, viewModel.relationship.value, ""),
                text = "회원 정보를 모두 입력했어요",
                route = NavigationRoutes.BabyRegisterScreen.route,
                navController = navController
            )
/*            PhoneAuthWidget(
                route = NavigationRoutes.AuthScreen.route,
                navController = navController
            )*/
        }
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
        placeholder = {
            Text(
                "닉네임을 입력하세요.",
                style = StartFontStyle.startBody1,
                color = Color(R.color.gray4)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.background_gray),
            focusedBorderColor = Color.Unspecified,
            cursorColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun CoParentCodeRegisterField(viewModel: UserRegisterViewModel, userId: String) {

    val coParentCode by viewModel.coParentCode.collectAsState()

    Row() {
        OutlinedTextField(
            modifier = Modifier
                .width(230.dp)
                .height(60.dp),
            value = coParentCode,
            onValueChange = { updatedCoParentCode ->
                viewModel.coParentCode.value = updatedCoParentCode
            },
            placeholder = {
                Text(
                    "공동양육자 코드",
                    style = StartFontStyle.startBody1,
                    color = Color(R.color.gray4)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.background_gray),
                focusedBorderColor = Color.Unspecified,
                cursorColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = colorResource(id = R.color.secondary_color),
            ),
            onClick = {
                viewModel.addBabyCode(userId, coParentCode)
            },
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevation(0.dp,0.dp)
        ) {
            Text(
                "코드 인증",
                style = StartFontStyle.startButton,
                color = colorResource(id = R.color.secondary_color)
            )
        }
    }
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
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                textStyle = TextStyle(
                    fontFamily = nanumSquare,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.secondary_color)
                ),
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.background_main),
                    focusedBorderColor = Color.Unspecified,
                    cursorColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
            )
            DropdownMenu(
                expanded = expanded,
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = { expanded = false },
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
fun RegisterButton(
    isNotNull: Boolean,
    viewModel: UserRegisterViewModel,
    user: User,
    text: String,
    route: String,
    navController: NavController
) {

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        val userRegistrationState = viewModel.userRegistrationState.collectAsState().value
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                if (isNotNull) {
                    viewModel.setUserInfoToSP(context, user)
                    viewModel.registerUser(user) // 버튼 클릭 시 사용자 등록 함수 호출
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = colorResource(id = R.color.secondary_color),
            ),
            elevation = ButtonDefaults.elevation(0.dp,0.dp)
        ) {
            Text(
                text, style = StartFontStyle.startButton,
                color = colorResource(id = R.color.secondary_color)
            )
        }

        LaunchedEffect(userRegistrationState) {
            when (userRegistrationState) {
                is Resource.Success -> {
                    Log.d("userRegisterScreen", "유저 등록 성공")
                    Log.d("userRegisterScreen", route)
                    navController.navigate(route)
                }
                is Resource.Error -> {
                    // 오류가 발생한 경우 로그 출력
                    Log.d("RegisterButton", "API 오류: ${userRegistrationState.message}")
                }
                is Resource.Loading -> {
                    // 필요한 경우 로딩 상태 처리
                }
            }
        }
    }
}
