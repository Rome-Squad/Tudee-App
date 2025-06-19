package com.giraffe.tudeeapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.tudeeapp.presentation.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.tasksRoute(
    navController: NavController
) {
    composable(
        route = "${Screen.TaskScreen.route}/{${TasksArgs.TAB_INDEX}}",
        arguments = listOf(
            navArgument(TasksArgs.TAB_INDEX) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        TaskScreen(
            currentTabIndex = TasksArgs(savedStateHandle = backStackEntry.savedStateHandle).tabIndex
        )
    }
}

class TasksArgs(savedStateHandle: SavedStateHandle) {
    val tabIndex: Int = savedStateHandle.get<Int>(TAB_INDEX) ?: 0
    companion object {
        const val TAB_INDEX = "TAB_INDEX"
    }
}