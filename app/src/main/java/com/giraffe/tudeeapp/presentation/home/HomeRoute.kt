package com.giraffe.tudeeapp.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.navigateToTaskScreen

fun NavGraphBuilder.homeRoute(
    navController: NavController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
) {
    composable(Screen.HomeScreen.route) {
        HomeScreen(
            isDarkTheme = isDarkTheme,
            onThemeSwitchToggle = onToggleTheme,
            navigateToTasksScreen = {
                navController.navigateToTaskScreen(it)
            }

        )
    }
}

class HomeArgs(savedStateHandle: SavedStateHandle) {
    // handle screen args here to make navigation more cleaner

    companion object {

    }
}