package com.giraffe.tudeeapp.presentation.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen

fun NavGraphBuilder.tasksRoute(navController: NavController? = null) {
    composable(Screen.TaskScreen.route) {
        TaskScreen()
    }
}

class TasksArgs(savedStateHandle: SavedStateHandle) {
    // handle screen args here to make navigation more cleaner

    companion object {

    }
}