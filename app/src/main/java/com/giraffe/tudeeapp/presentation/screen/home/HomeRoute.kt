package com.giraffe.tudeeapp.presentation.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.navigateToTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute(
    navController: NavController,
) {
    composable(Screen.HomeScreen.route) {
        HomeScreen(
            navigateToTasksScreen = {
                navController.navigateToTaskScreen(it)
            }

        )
    }
}