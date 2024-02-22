package com.example.baby.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyInfo
import com.example.baby.data.CoParents
import com.example.baby.data.UserResponse
import com.example.baby.network.Resource
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.BabyRegisterViewModel
import com.example.baby.viewModel.UserRegisterViewModel
import java.time.format.DateTimeFormatter

@Composable
fun MyPageScreen(
    viewModel: BabyRegisterViewModel,
    userViewModel: UserRegisterViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    var coParents by remember { mutableStateOf<List<CoParents>?>(null) }

    val babyId = SharedPreferenceUtil(context).getString("babyId", "")!!.toInt()

    LaunchedEffect(babyId) {
        babyId.let {
            coParents = viewModel.getCoParentsByBabyId(it)
            userViewModel.getUserInfo(SharedPreferenceUtil(context).getString("uid", "").toString())
            viewModel.getBabyInfoByBabyId(it)
        }
    }

    LaunchedEffect(true) {
        userViewModel.getUserInfo(SharedPreferenceUtil(context).getString("uid", "").toString())
        viewModel.getBabyInfoByBabyId(15)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.sub_color),
                elevation = 0.dp,
                title = {
                    Text(
                        "마이 페이지",
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
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = innerPadding.calculateBottomPadding() + 10.dp
                    )
            ) {
                babyInfoCard(viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                UserInfoCard(userViewModel, viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "공동양육자예요",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                CoParentInfoCard(viewModel = viewModel, coParents = coParents)
            }
        }
    }
}

@Composable
fun babyInfoCard(viewModel: BabyRegisterViewModel) {

    val state by viewModel.babyInfoGetState.collectAsState()

    when (state) {
        is Resource.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val baby = (state as Resource.Success<BabyInfo>).data

            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val babyBirth = baby!!.dateTime.format(formatter)

            val month = viewModel.calculateBabyMonth(baby.dateTime)
            val gender = if (baby.gender == "남") R.drawable.man_icon else R.drawable.woman_icon

            Card(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = colorResource(id = R.color.sub_color),
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Box(
                        modifier = Modifier.size(100.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.teddy_bear),
                            contentDescription = "babyPhoto",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(15.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row() {
                                Text(
                                    baby.name,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(id = R.color.secondary_color),
                                    fontSize = 25.sp
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Image(
                                    painter = painterResource(
                                        id = gender
                                    ),
                                    contentDescription = "gender",
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit_icon),
                                    contentDescription = "edit baby",
                                    tint = colorResource(
                                        id = R.color.secondary_light
                                    )
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            babyBirth,
                            color = colorResource(id = R.color.secondary_color),
                            fontWeight = FontWeight.SemiBold
                        )
                        Row() {
                            Text(
                                "생후",
                                color = colorResource(id = R.color.secondary_light),
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                "${month}개월",
                                color = colorResource(id = R.color.secondary_color),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row() {
                            Text(
                                "키",
                                color = colorResource(id = R.color.secondary_light),
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                "${baby.birthHeight}cm",
                                color = colorResource(id = R.color.secondary_color),
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(20.dp))
                            Text(
                                "몸무게",
                                color = colorResource(id = R.color.secondary_light),
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                "${baby.birthWeight}kg",
                                color = colorResource(id = R.color.secondary_color),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Column {
                Text(
                    "우리 아기", fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 20.sp
                )
                Text("자녀 정보 조회에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserInfoCard(viewModel: UserRegisterViewModel, babyViewModel: BabyRegisterViewModel) {

    val userInfoState by viewModel.userInfoState.collectAsState()

    when (userInfoState) {
        is Resource.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val userInfo = (userInfoState as Resource.Success<UserResponse>).data
            Column() {
                Text(
                    "내 정보는요",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp
                ) {
                    Row(modifier = Modifier.padding(10.dp)) {
                        Box(
                            modifier = Modifier.size(100.dp)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.teddy_bear),
                                contentDescription = "babyPhoto",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(15.dp)),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    userInfo!!.name,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(id = R.color.secondary_color),
                                    fontSize = 20.sp
                                )
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "edit profile",
                                        tint = colorResource(
                                            id = R.color.gray2
                                        )
                                    )
                                }
                            }
                            Spacer(Modifier.height(5.dp))
                            Text(
                                "${babyViewModel.name.value} ${userInfo!!.relation}",
                                color = colorResource(id = R.color.secondary_color),
                                fontWeight = FontWeight.SemiBold
                            )
                            Row() {
                                Text(
                                    "고유 코드",
                                    color = colorResource(id = R.color.secondary_light),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    babyViewModel.babyCode.value.toString(),
                                    color = colorResource(id = R.color.secondary_color),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Column {
                Text(
                    "내 정보는요", fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 20.sp
                )
                Text("유저 정보 조회에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CoParentInfoCard(viewModel: BabyRegisterViewModel, coParents: List<CoParents>?) {
    val context = LocalContext.current

    val babyIdStr = SharedPreferenceUtil(context).getString("babyId", "")
    val babyId = try {
        if (babyIdStr!!.all { it.isDigit() }) babyIdStr.toInt() else null
    } catch (e: NumberFormatException) {
        Log.d("애기 오류", e.toString())
    }

    val state by viewModel.coParentsGetState.collectAsState()

    val babyName by viewModel.name.collectAsState()

    when (state) {
        is Resource.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val coParents = (state as Resource.Success<List<CoParents>>).data
            if (coParents?.size == 0) {
                Text(
                    "아직 공동양육자가 없어요!", fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 16.sp
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(coParents!!.size) { index ->
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            backgroundColor = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 2.dp
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                Box(
                                    modifier = Modifier.size(100.dp)
                                ){
                                    Image(
                                        painter = painterResource(id = R.drawable.teddy_bear),
                                        contentDescription = "babyPhoto",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(15.dp)),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            coParents[index].name,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorResource(id = R.color.secondary_color),
                                            fontSize = 20.sp
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "edit profile",
                                            tint = Color.White,
                                            modifier = Modifier.size(47.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(5.dp))
                                    Text(
                                        babyName + " " + coParents[index].relation,
                                        color = colorResource(id = R.color.secondary_color),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row() {
                                        Text(
                                            "고유 코드",
                                            color = colorResource(id = R.color.secondary_light),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            viewModel.babyCode.value.toString(),
                                            color = colorResource(id = R.color.secondary_color),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Text("공동양육자 조회에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
        }
    }
}