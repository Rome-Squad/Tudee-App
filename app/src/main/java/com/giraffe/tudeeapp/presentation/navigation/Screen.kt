package com.giraffe.tudeeapp.presentation.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splashScreen")
    object HomeScreen : Screen("homeScreen")
    object CategoriesScreen : Screen("categoriesScreen")
    object TaskScreen : Screen("taskScreen")

    object OnboardingScreen : Screen("onboardingScreen")

}