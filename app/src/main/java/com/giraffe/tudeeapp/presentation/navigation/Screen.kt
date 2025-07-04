package com.giraffe.tudeeapp.presentation.navigation

open class Screen(val route: String) {
    object SplashScreen : Screen("splashScreen")
    object HomeScreen : Screen("homeScreen")
    object CategoriesScreen : Screen("categoriesScreen")
    object TasksByCategoryScreen : Screen("tasksByCategoryScreen")
    object TaskScreen : Screen("taskScreen")
    object OnboardingScreen : Screen("onboardingScreen")
}