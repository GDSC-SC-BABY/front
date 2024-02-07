package com.example.baby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baby.data.NavigationRoutes
import com.example.baby.network.AuthRepository
import com.example.baby.network.UserRepository
import com.example.baby.screen.*
import com.example.baby.ui.theme.BabyTheme
import com.example.baby.viewModel.*
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {


    private val dateViewModel by viewModels<DateViewModel>()

    private val calendarViewModel by viewModels<CalendarViewModel>()

    private val loadingViewModel by viewModels<LoadingViewModel>()

    private val babyFoodRegisterViewModel by viewModels<BabyFoodRegisterViewModel>()
    private val babyRegisterViewModel by viewModels<BabyRegisterViewModel>{
        BabyRegisterViewModelFactory(UserRepository())
    }

    private val babySnackRegisterViewModel by viewModels<BabySnackRegisterViewModel>()

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository(this@MainActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);


        setContent {
            BabyTheme {

                val navController = rememberNavController()

                // NavHost 설정
                NavHost(navController = navController, startDestination = "mainScreen") {
/*                    composable(NavigationRoutes.LoadingScreen.route) {
                        LoadingScreen(viewModel = loadingViewModel, navController = navController)
                    }*/
                    composable(NavigationRoutes.MainScreen.route) {
                        MainScreen(viewModel = calendarViewModel, navController = navController)
                    }
                    composable(NavigationRoutes.GuideScreen.route) {
                        GuideScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable(NavigationRoutes.PolicyScreen.route) {
                        PolicyScreen(viewModel = loadingViewModel, navController = navController)
                    }
                    composable(NavigationRoutes.MyPageScreen.route) {
                        MyPageScreen(viewModel = babyRegisterViewModel, navController = navController)
                    }
                    composable(NavigationRoutes.FoodRegisterScreen.route) {
                        BabyFoodRegisterScreen(
                            viewModel = dateViewModel,
                            babyFoodViewModel = babyFoodRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable(NavigationRoutes.SnackRegisterScreen.route) {
                        BabySnackRegisterScreen(
                            viewModel = dateViewModel,
                            babySnackViewModel = babySnackRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable(NavigationRoutes.AuthScreen.route) {
                        AuthScreen(viewModel = authViewModel, navController = navController)
                    }
                    composable(NavigationRoutes.FoodDetailScreen.route) {
                        BabyFoodDetailScreen(
                            viewModel = dateViewModel,
                            babyFoodViewModel = babyFoodRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable(NavigationRoutes.BabyPatternRecordScreen.route) {
                        BabyPatternRecordPage(
                            viewModel = loadingViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}