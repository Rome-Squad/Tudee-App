package com.giraffe.tudeeapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("homeScreen")
    object CategoriesScreen : Screen("categoriesScreen")
    object TaskScreen : Screen("taskScreen")
}