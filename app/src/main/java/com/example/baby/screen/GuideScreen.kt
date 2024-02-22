package com.example.baby.screen

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.baby.R
import com.example.baby.data.Guide
import com.example.baby.network.Resource
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.util.CustomBottomNavigation
import com.example.baby.viewModel.GuideViewModel
import com.example.baby.viewModel.LoadingViewModel
import java.util.Timer
import java.util.logging.Handler

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GuideScreen(viewModel: GuideViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "육아 정보",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            )
        },
        bottomBar = { CustomBottomNavigation(navController = navController) },
    ) {

        LaunchedEffect(Unit) {
            viewModel.getGuideList()
        }

        val guideData by viewModel.guideDataState.collectAsState()


        when (val state = guideData) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                val guideData = state.data
                Log.d("guideData", state.data.toString())
                if (guideData != null && guideData.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        items(guideData) { guide ->
                            guideCard(guide)
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "생활 패턴을 입력해주세요",
                        style = StartFontStyle.startBody1,
                        color = Color(R.color.gray6),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        textAlign = TextAlign.Center,
                    )
                }

            }
            is Resource.Error -> {
            }
        }


    }
}

@Composable
fun guideCard(guideData: Guide) {
    Card(
        border = BorderStroke(1.dp, colorResource(id = R.color.gray1)),
        modifier = Modifier.height(100.dp)
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(3f).padding(start = 20.dp)
            ) {
                Text("${guideData.age}개월", style = StartFontStyle.startBody1, color = Color(R.color.gray4))
                Text(
                    guideData.title, style = StartFontStyle.startButton,
                    color = colorResource(id = R.color.secondary_color)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = guideData.imageUrl,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .weight(1f)
                //contentScale = ContentScale.FillBounds
            )
        }
    }

}