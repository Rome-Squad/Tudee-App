package com.giraffe.tudeeapp.presentation.tasks_by_category

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.tudeeapp.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.tasksByCategoryRoute(navController: NavController) {
    composable(
        route = "${Screen.TasksByCategoryScreen.route}/{${CategoriesArgs.CATEGORY_ID}}",
        arguments = listOf(navArgument(CategoriesArgs.CATEGORY_ID) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        val viewModel: TasksByCategoryViewModel = koinViewModel(
            viewModelStoreOwner = backStackEntry
        )

        TasksByCategoryScreen(
            viewModel = viewModel,
            onBackClick = { navController.popBackStack() }
        )
    }
}

class CategoriesArgs(savedStateHandle: SavedStateHandle) {
    val categoryId: Long = savedStateHandle.get<Long>(CATEGORY_ID) ?: 0L

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }
}