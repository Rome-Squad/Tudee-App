package com.giraffe.tudeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.categories.CategoriesScreen
import com.giraffe.tudeeapp.presentation.home.HomeScreen
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.splash.onboard.OnboardingScreen
import com.giraffe.tudeeapp.presentation.splash.splashscreen.SplashScreen
import com.giraffe.tudeeapp.presentation.tasks.TaskScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                val noBottomBarRoutes = listOf(
                    Screen.SplashScreen.route,
                    Screen.OnboardingScreen.route
                )

                val showBottomBar = currentRoute !in noBottomBarRoutes

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->

                    val paddingModifier = if (currentRoute in noBottomBarRoutes) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route,
                        modifier = paddingModifier
                    ) {
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

                        composable(Screen.OnboardingScreen.route) {
                            OnboardingScreen(
                                onFinish = {
                                    navController.navigate(Screen.HomeScreen.route) {
                                        popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.HomeScreen.route) {
                            HomeScreen()
                        }

                        composable(Screen.TaskScreen.route) {
                            TaskScreen()
                        }

                        composable(Screen.CategoriesScreen.route) {
                            CategoriesScreen()
                        }
                    }
                }
            }
        }
    }
}