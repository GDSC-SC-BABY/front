package com.example.baby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baby.data.BabyFood
import com.example.baby.data.NavigationRoutes
import com.example.baby.network.AuthRepository
import com.example.baby.network.BabyFoodRepository
import com.example.baby.network.BabyPatternRepository
import com.example.baby.network.BabyRepository
import com.example.baby.network.BabySnackRepository
import com.example.baby.network.ImageRepository
import com.example.baby.network.UserRepository
import com.example.baby.screen.*
import com.example.baby.ui.theme.BabyTheme
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.*
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {


    private val imageUploadViewModel by viewModels<ImageUploadViewModel> {
        // Application 인스턴스와 함께 ImageRepository 인스턴스를 Factory에 전달
        ImageUploadViewModelFactory(application, ImageRepository(applicationContext))
    }


    private val dateViewModel by viewModels<DateViewModel>()

    private val calendarViewModel by viewModels<CalendarViewModel>()

    private val loadingViewModel by viewModels<LoadingViewModel>()


    private val babyFoodViewModel by viewModels<BabyFoodViewModel> {
        BabyFoodViewModelFactory(BabyFoodRepository())
    }

    private val babyRegisterViewModel by viewModels<BabyRegisterViewModel> {
        BabyRegisterViewModelFactory(BabyRepository())
    }

    private val babyPatternRecordViewModel by viewModels<BabyPatternRecordViewModel> {
        BabyPatternRecordViewModelFactory(BabyPatternRepository())
    }
    private val babyPatternViewModel by viewModels<BabyPatternViewModel> {
        BabyPatternViewModelFactory(BabyPatternRepository())
    }

    private val babySnackRegisterViewModel by viewModels<BabySnackRegisterViewModel> {
        SnackViewModelFactory(BabySnackRepository())
    }

    private val userRegisterViewModel: UserRegisterViewModel by viewModels {
        UserViewModelFactory(UserRepository())
    }

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
                        MyPageScreen(
                            viewModel = babyRegisterViewModel,
                            userViewModel = userRegisterViewModel,
                            navController = navController
                        )
                    }
                    composable(NavigationRoutes.DayBabyFoodScreen.route, arguments = listOf(
                        navArgument("year") { type = NavType.IntType },
                        navArgument("month") { type = NavType.IntType },
                        navArgument("day") { type = NavType.IntType }
                    )
                    ) { entry ->
                        DayBabyFoodScreen(
                            viewModel = babyFoodViewModel,
                        navController = navController,
                        year = entry.arguments?.getInt("year")!!,
                        month = entry.arguments?.getInt("month")!!,
                        day = entry.arguments?.getInt("day")!!
                        )
                    }
                    composable(NavigationRoutes.DaySnackScreen.route, arguments = listOf(
                        navArgument("year") { type = NavType.IntType },
                        navArgument("month") { type = NavType.IntType },
                        navArgument("day") { type = NavType.IntType }
                    )
                    ) { entry ->
                        DaySnackScreen(
                            viewModel = babySnackRegisterViewModel,
                            navController = navController,
                            year = entry.arguments?.getInt("year")!!,
                            month = entry.arguments?.getInt("month")!!,
                            day = entry.arguments?.getInt("day")!!
                        )
                    }
                    composable(NavigationRoutes.FoodRegisterScreen.route) {
                        BabyFoodRegisterScreen(
                            viewModel = imageUploadViewModel,
                            babyFoodViewModel = babyFoodViewModel,
                            imageViewModel = imageUploadViewModel,
                            navController = navController
                        )
                    }
                    composable(NavigationRoutes.SnackRegisterScreen.route) {
                        BabySnackRegisterScreen(
                            viewModel = imageUploadViewModel,
                            dateViewModel = dateViewModel,
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
                            babyFoodViewModel = babyFoodViewModel,
                            navController = navController
                        )
                    }
                    composable(
                        route = "${NavigationRoutes.BabyPatternRecordScreen.route}/{selectedTabIndex}",
                        arguments = listOf(
                            navArgument("selectedTabIndex") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val selectedIndex =
                            backStackEntry.arguments?.getInt("selectedTabIndex") ?: 0

                        BabyPatternRecordPage(
                            viewModel = babyPatternRecordViewModel,
                            navController = navController,
                            selectedIndex = selectedIndex,
                            babyId = SharedPreferenceUtil(applicationContext).getString("babyId", "1")?.toInt()?:1
                        )
                    }
                    composable(NavigationRoutes.BabyPatternScreen.route) {
                        BabyPatternPage(
                            viewModel = babyPatternViewModel,
                            navController = navController,
                            babyId = SharedPreferenceUtil(applicationContext).getString("babyId", "1")?.toInt()?:1
                        )
                    }

                    composable(NavigationRoutes.RegisterScreen.route) {
                        UserRegisterScreen(
                            userId = "asdfb",
                            viewModel = userRegisterViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}