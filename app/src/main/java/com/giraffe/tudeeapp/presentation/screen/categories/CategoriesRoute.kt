package com.giraffe.tudeeapp.presentation.screen.categories

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.navigateToTaskByCategoryScreen

fun NavGraphBuilder.categoriesRoute(navController: NavController) {
    composable(Screen.CategoriesScreen.route) {
        CategoriesScreen(navigateToTaskByCategoryScreen = navController::navigateToTaskByCategoryScreen)
    }
}