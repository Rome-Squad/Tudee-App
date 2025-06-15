package com.giraffe.tudeeapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.design_system.component.TudeeAppBar

@Composable
fun HomeScreen(
) {
    Column() {
        TudeeAppBar(
            isDarkTheme = true,
            onThemeSwitchToggle = {}
        )

    }
}
@Preview(
    widthDp = 360,
)
@Composable
fun Preview() {
    HomeScreen()
}