package com.giraffe.tudeeapp.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.navigateToTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
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