package com.giraffe.tudeeapp.presentation.categories

import androidx.lifecycle.SavedStateHandle
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

class CategoriesArgs(savedStateHandle: SavedStateHandle) {
    // handle screen args here to make navigation more cleaner

    companion object {

    }
}