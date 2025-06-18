package com.giraffe.tudeeapp.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.tudeeapp.presentation.navigation.Screen

fun NavGraphBuilder.homeRoute(navController: NavController? = null) {
    composable(Screen.HomeScreen.route) {
        HomeScreen()
    }
}

class HomeArgs(savedStateHandle: SavedStateHandle) {
    // handle screen args here to make navigation more cleaner

    companion object {

    }
}