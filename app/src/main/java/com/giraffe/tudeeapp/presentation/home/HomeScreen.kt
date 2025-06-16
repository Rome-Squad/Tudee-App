package com.giraffe.tudeeapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
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