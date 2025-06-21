package com.giraffe.tudeeapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.TudeeNavGraph
import com.giraffe.tudeeapp.presentation.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            TudeeTheme(
                isDarkTheme = mainViewModel.isDarkTheme
            ) {
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
                    TudeeNavGraph(
                        modifier = paddingModifier,
                        navController = navController,
                        isDarkTheme = mainViewModel.isDarkTheme,
                        onToggleTheme = { mainViewModel.onToggleTheme() }
                    )
                }
            }
        }
    }
}