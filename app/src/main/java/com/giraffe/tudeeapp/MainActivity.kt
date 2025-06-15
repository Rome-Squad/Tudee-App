package com.giraffe.tudeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.categories.CategoriesScreen
import com.giraffe.tudeeapp.presentation.home.HomeScreen
import com.giraffe.tudeeapp.presentation.splash.SplashScreen
import com.giraffe.tudeeapp.presentation.tasks.TaskScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                val showBottomBar = currentRoute != Screen.SplashScreen.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->

                    val paddingModifier = if (currentRoute == Screen.SplashScreen.route) {
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
                            SplashScreen(onTimeout = {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                }
                            })
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