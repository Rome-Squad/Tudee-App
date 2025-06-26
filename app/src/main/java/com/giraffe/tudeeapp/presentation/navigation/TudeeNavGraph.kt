package com.giraffe.tudeeapp.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.BottomNavigationItem
import com.giraffe.tudeeapp.design_system.component.DefaultNavigationBar
import com.giraffe.tudeeapp.presentation.screen.categories.categoriesRoute
import com.giraffe.tudeeapp.presentation.screen.home.homeRoute
import com.giraffe.tudeeapp.presentation.screen.onboard.onboardingRoute
import com.giraffe.tudeeapp.presentation.screen.splash.splashRoute
import com.giraffe.tudeeapp.presentation.screen.tasks.tasksRoute
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.tasksByCategoryRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph(modifier: Modifier = Modifier) {
    val screensWithoutBottomNav = listOf(
        Screen.SplashScreen.route,
        Screen.OnboardingScreen.route,
        Screen.TasksByCategoryScreen.route,
    )
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomNav =
        currentRoute != null && !screensWithoutBottomNav.any { currentRoute.contains(it) }
    val animationTime = 500
    val animatedPadding = animateDpAsState(
        targetValue = if (showBottomNav) 74.dp else 0.dp,
        animationSpec = tween(animationTime)
    )
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically(
                    animationSpec = tween(animationTime),
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(animationTime),
                    targetOffsetY = { it }),
            ) {
                DefaultNavigationBar(
                    modifier = Modifier.height(74.dp),
                    navController = navController,
                    items = listOf(
                        BottomNavigationItem(
                            route = Screen.HomeScreen.route,
                            selectedIcon = R.drawable.home_selected,
                            unselectedIcon = R.drawable.home_unselected
                        ),
                        BottomNavigationItem(
                            route = "${Screen.TaskScreen.route}/${0}",
                            selectedIcon = R.drawable.task_selected,
                            unselectedIcon = R.drawable.tasks_unselected
                        ),
                        BottomNavigationItem(
                            route = Screen.CategoriesScreen.route,
                            selectedIcon = R.drawable.categories_selected,
                            unselectedIcon = R.drawable.categories_unselected
                        )
                    )
                )
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = modifier.padding(bottom = animatedPadding.value)
        ) {
            splashRoute(navController)
            onboardingRoute(navController)
            homeRoute(navController)
            tasksRoute()
            categoriesRoute(navController)
            tasksByCategoryRoute(navController)
        }
    }
}

fun NavController.navigateToTaskScreen(tabIndex: Int) {
    navigate("${Screen.TaskScreen.route}/$tabIndex")
}

fun NavController.navigateToTaskByCategoryScreen(categoryId: Long) {
    navigate("${Screen.TasksByCategoryScreen.route}/${categoryId}")
}