package com.giraffe.tudeeapp

sealed class Screen(val route: String) {
    object HomeScreen : Screen("homeScreen")
    object CategoriesScreen : Screen("categoriesScreen")
    object TaskScreen : Screen("taskScreen")
}