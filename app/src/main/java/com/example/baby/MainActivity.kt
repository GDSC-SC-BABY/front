package com.example.baby

import LoginPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baby.network.AuthRepository
import com.example.baby.screen.*
import com.example.baby.ui.theme.BabyTheme
import com.example.baby.viewModel.*

class MainActivity : ComponentActivity() {

    private val dateViewModel by viewModels<DateViewModel>()

    private val calendarViewModel by viewModels<CalendarViewModel>()

    private val loadingViewModel by viewModels<LoadingViewModel>()

    private val loginViewModel by viewModels<LoginViewModel>()

    private val userRegisterViewModel by viewModels<UserRegisterViewModel>()

    private val babyFoodRegisterViewModel by viewModels<BabyFoodRegisterViewModel>()

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository(this@MainActivity))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BabyTheme {

                val navController = rememberNavController()

                // NavHost 설정
                NavHost(navController = navController, startDestination = "loadingScreen") {
                    composable("loadingScreen") {
                        LoadingScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable("registerScreen") {
                        UserRegisterPage(
                            viewModel = userRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable("loginScreen") {
                        LoginPage(
                            viewModel = loginViewModel,
                            navController = navController,
                            {}
                        )
                    }
                    composable("mainScreen") {
                        MainScreen(viewModel = calendarViewModel, navController = navController)
                    }
                    composable("guideScreen") {
                        GuideScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable("policyScreen") {
                        PolicyScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable("myPageScreen") {
                        MyPageScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable("babyRegisterScreen") {
                        BabyRegisterPage(
                            viewModel = loadingViewModel,
                            navController = navController
                        )
                    }
                    composable("foodRegisterScreen") {
                        BabyFoodRegisterScreen(
                            viewModel = dateViewModel,
                            babyFoodViewModel = babyFoodRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable("snackRegisterScreen") {
                        BabySnackRegisterScreen(
                            viewModel = dateViewModel,
                            navController = navController
                        )
                    }
                    composable("authScreen") {
                        AuthScreen(
                            viewModel = authViewModel,
                            navController = navController
                        )
                    }

                    composable("foodDetailScreen") {
                        BabyFoodDetailScreen(
                            viewModel = dateViewModel,
                            babyFoodViewModel = babyFoodRegisterViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}