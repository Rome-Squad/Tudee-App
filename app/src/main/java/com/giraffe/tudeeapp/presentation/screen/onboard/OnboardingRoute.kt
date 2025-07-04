package com.giraffe.tudeeapp.presentation.screen.onboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen

fun NavGraphBuilder.onboardingRoute(navController: NavController) {
    composable(Screen.OnboardingScreen.route) {
        OnboardingScreen(
            onFinish = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                }
            }
        )
    }
}
