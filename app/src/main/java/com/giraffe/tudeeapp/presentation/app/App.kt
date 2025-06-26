package com.giraffe.tudeeapp.presentation.app

import android.os.Build
import androidx.activity.SystemBarStyle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.navigation.TudeeNavGraph
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit) {
    val appViewModel = koinViewModel<AppViewModel>()
    val state by appViewModel.state.collectAsStateWithLifecycle()
    state.isDarkTheme?.let { isDarkTheme ->
        handleStatusBar(enableEdgeToEdge, isDarkTheme)
        TudeeTheme(isDarkTheme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
            ) {
                TudeeNavGraph()
            }
        }
    }
}

private fun handleStatusBar(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit,
    isDarkTheme: Boolean
) {
    val systemBarsColor = if (isDarkTheme)
        SystemBarStyle.dark(Color.Transparent.toArgb())
    else
        SystemBarStyle.light(
            Color.Transparent.toArgb(),
            Color.Transparent.toArgb()
        )
    enableEdgeToEdge(systemBarsColor, systemBarsColor)
}