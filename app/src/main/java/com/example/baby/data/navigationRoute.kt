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
    object BabyPatternRecordScreen : NavigationRoutes("patternRecordScreen")
    object BabyPatternScreen : NavigationRoutes("patternScreen")
    object MemberTypeScreen : NavigationRoutes("memberTypeScreen")
    object DayBabyFoodScreen : NavigationRoutes("dayBabyFoodScreen/{year}/{month}/{day}") {
        fun createRoute(year: Int, month: Int, day: Int) = "dayBabyFoodScreen/$year/$month/$day"
    }

    object DaySnackScreen : NavigationRoutes("daySnackScreen/{year}/{month}/{day}") {
        fun createRoute(year: Int, month: Int, day: Int) = "daySnackScreen/$year/$month/$day"
    }
}
