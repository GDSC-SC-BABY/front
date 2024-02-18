package com.example.baby.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.data.UserResponse
import com.example.baby.network.Resource
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.BabyRegisterViewModel
import com.example.baby.viewModel.UserRegisterViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyPageScreen(
    viewModel: BabyRegisterViewModel,
    userViewModel: UserRegisterViewModel,
    navController: NavController
) {

    LaunchedEffect(true) {
        userViewModel.getUserInfo("ztg4NhVNvgXpWMhxw3bx7k3p4SC2")
    }

    Scaffold(
        bottomBar = { CustomBottomNavigation(navController = navController) }
    ) { innerPadding ->
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 40.dp,
                        bottom = innerPadding.calculateBottomPadding() + 20.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                babyInfoCard()
                Spacer(modifier = Modifier.height(20.dp))
                userInfo(userViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                userCodeInfo()
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Co_parentInfo(viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Divider(thickness = 1.dp, color = Color.Black)
                Spacer(modifier = Modifier.height(20.dp))
                DarkModeSelect()
            }
        }
    }
}

@Composable
fun babyInfoCard() {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.teddy_bear),
                contentDescription = "babyPhoto",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        SharedPreferenceUtil(context).getString("babyName", "").toString(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(
                            id = SharedPreferenceUtil(context).getInt(
                                "genderIcon",
                                R.drawable.man_icon
                            )
                        ),
                        contentDescription = "gender",
                        modifier = Modifier.size(23.dp)
                    )
                }
                Text(SharedPreferenceUtil(context).getString("birth", "").toString())
                Text("6개월")
                Text("이유식 초기")
            }

        }
    }
}

@Composable
fun userInfo(viewModel: UserRegisterViewModel) {
    val context = LocalContext.current

    val userInfoState by viewModel.userInfoState.collectAsState()

    when (userInfoState) {
        is Resource.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val userInfo = (userInfoState as Resource.Success<UserResponse>).data

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("내 정보", fontWeight = FontWeight.Bold, fontSize = 23.sp)
                    IconButton(onClick = {
                        SharedPreferenceUtil(context).setString("relation", "아빠")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "updateUserIcon"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text("닉네임", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                        Text("관계", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                        Text("거주지", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                    }
                    Spacer(Modifier.width(45.dp))
                    Column() {
                        Text(userInfo!!.name)
                        Text("엄마")
                        Text(userInfo.residence)
                    }
                }
            }
        }

        is Resource.Error -> {
            Column {
                Text("내 정보", fontWeight = FontWeight.Bold, fontSize = 23.sp)
                Text("유저 정보 조회에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
            }
        }
    }
}

@Composable
fun userCodeInfo() {
    Column {
        Text("고유 코드", fontWeight = FontWeight.Bold, fontSize = 23.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "3v8duaod88u7")
    }
}

@Composable
fun Co_parentInfo(viewModel: BabyRegisterViewModel) {
    var relationDialogOpen by remember {
        mutableStateOf(false)
    }

    var nicknameDialogOpen by remember {
        mutableStateOf(false)
    }

    val index = viewModel.coParentRelations.size

    if (relationDialogOpen) {
        Co_parentRelationDialog(viewModel, {
            relationDialogOpen = false
        }) {
            relationDialogOpen = false
            nicknameDialogOpen = true
            Log.d("coparent", "추가하고는: ${viewModel.coParentRelations.size}")
        }
    }

    if (nicknameDialogOpen) {
        Co_parentNicknameDialog(viewModel, {
            viewModel.deleteCoParentRelation(index)
            Log.d("coparent", "취소하고는: ${viewModel.coParentRelations.size}")
            nicknameDialogOpen = false
        }) {
            nicknameDialogOpen = false
        }
    }

    Column {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
            Text("공동양육자", fontWeight = FontWeight.Bold, fontSize = 23.sp)
//            IconButton(onClick = { relationDialogOpen = true }) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = "updateUserIcon")
//            }
//        }
        Spacer(modifier = Modifier.height(5.dp))

        if (viewModel.coParentNicknames.isEmpty()) {
            Text("공동 양육자가 없어요!")
        }

        viewModel.coParentNicknames.forEachIndexed { index, _ ->
            Co_parentField(index, viewModel)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun DarkModeSelect() {
    var isChecked by remember { mutableStateOf(false) }
    Column {
        Text("고유 코드", fontWeight = FontWeight.Bold, fontSize = 23.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "다크모드")
            Switch(checked = isChecked, onCheckedChange = {
                isChecked = !isChecked
            }
            )
        }
    }
}


@Composable
fun Co_parentField(index: Int, viewModel: BabyRegisterViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                viewModel.coParentRelations[index],
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Spacer(Modifier.width(45.dp))
            Text(viewModel.coParentNicknames[index])
        }
        IconButton(onClick = {
            viewModel.deleteCoParentRelation(index)
            viewModel.deleteCoParentNickname(index)
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete Co-Parent")
        }
    }
}


@Composable
fun Co_parentRelationDialog(
    viewModel: BabyRegisterViewModel,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {

    val relations = listOf("엄마", "아빠", "시터")
    val selectedValue = remember { mutableStateOf("") } // 선택된 라디오 버튼에 해당하는 내용
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    AlertDialog(

        // 다이얼로그 뷰 밖의 화면 클릭시, 인자로 받은 함수 실행하며 다이얼로그 상태 변경
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = "공동양육자",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                Text(text = "아기와의 관계", modifier = Modifier.padding(bottom = 5.dp))
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    relations.forEach { item ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .selectable( // 선택 가능한 상태
                                        selected = isSelectedItem(item),
                                        onClick = { onChangeState(item) },
                                        role = Role.RadioButton
                                    )
                                    .padding(bottom = 3.dp)
                            ) {
                                RadioButton(
                                    selected = isSelectedItem(item),
                                    onClick = null,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                                Text(text = item)
                            }
                        }
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "취소", color = Color.Black)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.addCoParentRelation(selectedValue.value)
                    onConfirmRequest()
                }) {
                Text(text = "다음", color = Color.Black)
            }
        }
    )

}

@Composable
fun Co_parentNicknameDialog(
    viewModel: BabyRegisterViewModel,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {

    var selectedValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = "공동양육자",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                Text(text = "닉네임", modifier = Modifier.padding(bottom = 5.dp))

                TextField(
                    value = selectedValue,
                    onValueChange = {
                        selectedValue = it
                    }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "취소", color = Color.Black)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.addCoParentNickname(selectedValue)
                    onConfirmRequest()
                }) {
                Text(text = "추가", color = Color.Black)
            }
        }
    )
}