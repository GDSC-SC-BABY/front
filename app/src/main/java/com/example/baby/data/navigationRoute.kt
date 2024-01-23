package com.example.baby.data

sealed class NavigationRoutes(val route: String) {
    object LoadingScreen : NavigationRoutes("loadingScreen")
    object RegisterScreen : NavigationRoutes("registerScreen")
    object LoginScreen : NavigationRoutes("loginScreen")
    object MainScreen : NavigationRoutes("mainScreen")
    object GuideScreen : NavigationRoutes("guideScreen")
    object PolicyScreen : NavigationRoutes("policyScreen")
    object MyPageScreen : NavigationRoutes("myPageScreen")
    object BabyRegisterScreen : NavigationRoutes("babyRegisterScreen")
    object FoodRegisterScreen : NavigationRoutes("foodRegisterScreen")
    object SnackRegisterScreen : NavigationRoutes("snackRegisterScreen")
    object AuthScreen : NavigationRoutes("authScreen")
    object FoodDetailScreen : NavigationRoutes("foodDetailScreen")
}
