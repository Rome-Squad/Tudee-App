package com.giraffe.tudeeapp.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.color.darkThemeColor
import com.giraffe.tudeeapp.design_system.color.lightThemeColor
import com.giraffe.tudeeapp.design_system.drawables.LocalTudeeDrawables
import com.giraffe.tudeeapp.design_system.drawables.darkDrawables
import com.giraffe.tudeeapp.design_system.drawables.lightDrawables
import com.giraffe.tudeeapp.design_system.text_style.LocalTudeeTextStyle
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColor else lightThemeColor
    val drawables = if (isDarkTheme) darkDrawables else lightDrawables

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle,
        LocalTudeeDrawables provides drawables

    ) {
        content()
    }
}