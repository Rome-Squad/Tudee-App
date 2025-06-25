package com.giraffe.tudeeapp.presentation.screen.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen

fun NavGraphBuilder.splashRoute(navController: NavController) {
    composable(Screen.SplashScreen.route) {
        SplashScreen(
            navigateToHome = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            },
            navigateToOnboarding = {
                navController.navigate(Screen.OnboardingScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        )
    }
}
