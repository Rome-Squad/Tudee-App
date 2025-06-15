package com.giraffe.tudeeapp.design_system.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class TudeeColors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val primaryGradient: Gradient,
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,
    val emojiTint: Color,
    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val disable: Color,
    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
    val pinkAccent: Color,
    val yellowAccent: Color,
    val yellowVariant: Color,
    val greenAccent: Color,
    val greenVariant: Color,
    val purpleAccent: Color,
    val purpleVariant: Color,
    val error: Color,
    val errorVariant: Color,
    val overlay: Color
)

data class Gradient(
    val startGradient: Color,
    val endGradient: Color,
)

internal val LocalTudeeColors = staticCompositionLocalOf { lightThemeColor }
