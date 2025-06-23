package com.giraffe.tudeeapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.MainViewModel
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.navigation.TudeeNavGraph
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            enableEdgeToEdge(
                statusBarStyle =
                    if (mainViewModel.isDarkTheme)
                        SystemBarStyle.dark(Color.Transparent.toArgb())
                    else
                        SystemBarStyle.light(
                            Color.Transparent.toArgb(),
                            Color.Transparent.toArgb()
                        ),
            )
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
                Column(
                    modifier = Modifier.fillMaxSize()
                        .navigationBarsPadding()
                ) {
                    TudeeNavGraph(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        isDarkTheme = mainViewModel.isDarkTheme,
                        onToggleTheme = mainViewModel::onToggleTheme
                    )
                    if (showBottomBar) {
                        NavBar(navController = navController)
                    }
                }
            }
        }
    }
}