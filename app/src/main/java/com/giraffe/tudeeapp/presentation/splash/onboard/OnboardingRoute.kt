package com.giraffe.tudeeapp.presentation.splash.onboard

import androidx.lifecycle.SavedStateHandle
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

class OnboardingArgs(savedStateHandle: SavedStateHandle) {
    // handle screen args here to make navigation more cleaner

    companion object {

    }
}