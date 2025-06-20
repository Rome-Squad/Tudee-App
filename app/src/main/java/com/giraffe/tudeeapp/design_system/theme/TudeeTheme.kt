package com.giraffe.tudeeapp.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.color.darkThemeColor
import com.giraffe.tudeeapp.design_system.color.lightThemeColor
import com.giraffe.tudeeapp.design_system.resources.LocalTudeeResources
import com.giraffe.tudeeapp.design_system.resources.LocalTudeeStrings
import com.giraffe.tudeeapp.design_system.resources.arStrings
import com.giraffe.tudeeapp.design_system.resources.darkResources
import com.giraffe.tudeeapp.design_system.resources.defaultStrings
import com.giraffe.tudeeapp.design_system.resources.lightResources
import com.giraffe.tudeeapp.design_system.text_style.LocalTudeeTextStyle
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = false,
    isArabic: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColor else lightThemeColor
    val resources = if (isDarkTheme) darkResources else lightResources
    val strings = if (isArabic) arStrings else defaultStrings
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle,
        LocalTudeeResources provides resources,
        LocalTudeeStrings provides strings,
        LocalLayoutDirection provides layoutDirection
    ) {
        content()
    }
}