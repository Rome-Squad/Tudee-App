package com.giraffe.tudeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.tudeeapp.presentation.categories.categoriesRoute
import com.giraffe.tudeeapp.presentation.home.homeRoute
import com.giraffe.tudeeapp.presentation.splash.onboard.onboardingRoute
import com.giraffe.tudeeapp.presentation.splash.splashscreen.splashRoute
import com.giraffe.tudeeapp.presentation.tasks.tasksRoute

@Composable
fun TudeeNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        splashRoute(navController)
        onboardingRoute(navController)
        homeRoute(navController)
        tasksRoute(navController)
        categoriesRoute(navController)
    }
}