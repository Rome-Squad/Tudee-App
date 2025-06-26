package com.giraffe.tudeeapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.AppViewModel
import com.giraffe.tudeeapp.presentation.navigation.TudeeNavGraph
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appViewModel = koinViewModel<AppViewModel>()
            val state by appViewModel.state.collectAsStateWithLifecycle()
            state.isDarkTheme?.let { isDarkTheme ->
                val systemBarsColor = if (isDarkTheme)
                    SystemBarStyle.dark(Color.Transparent.toArgb())
                else
                    SystemBarStyle.light(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb()
                    )
                enableEdgeToEdge(
                    statusBarStyle = systemBarsColor,
                    navigationBarStyle = systemBarsColor
                )
                TudeeTheme(
                    isDarkTheme = isDarkTheme
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Transparent,
                    ) {
                        TudeeNavGraph()
                    }
                }
            }


        }
    }
}