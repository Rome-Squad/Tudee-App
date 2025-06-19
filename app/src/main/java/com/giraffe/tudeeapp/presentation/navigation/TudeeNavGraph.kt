package com.giraffe.tudeeapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.tudeeapp.presentation.categories.categoriesRoute
import com.giraffe.tudeeapp.presentation.home.homeRoute
import com.giraffe.tudeeapp.presentation.splash.onboard.onboardingRoute
import com.giraffe.tudeeapp.presentation.splash.splashscreen.splashRoute
import com.giraffe.tudeeapp.presentation.tasks.tasksRoute
import com.giraffe.tudeeapp.presentation.tasks_by_category.tasksByCategoryRoute

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onToggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        splashRoute(navController)
        onboardingRoute(navController)
        homeRoute(
            navController = navController,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )
        tasksRoute(navController)
        categoriesRoute(navController)
        tasksByCategoryRoute(navController)
    }
}

fun NavController.navigateToTaskScreen(tabIndex: Int) {
    navigate("${Screen.TaskScreen.route}/$tabIndex")
}

fun NavController.navigateToTaskByCategoryScreen(categoryId: Long) {
    navigate("${Screen.TasksByCategoryScreen.route}/${categoryId}")
}

fun NavController.navigateToTaskScreen() {
    navigate(Screen.TaskScreen.route)
}