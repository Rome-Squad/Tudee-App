package com.giraffe.tudeeapp.presentation.splash.splashscreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen


fun NavGraphBuilder.splashRoute(navController: NavController) {
    composable(Screen.SplashScreen.route) {
        SplashScreen(
            onOnboardingShown = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            },
            onOnboardingNotShown = {
                navController.navigate(Screen.OnboardingScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        )
    }
}
