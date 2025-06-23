package com.giraffe.tudeeapp.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.color.TudeeColors
import com.giraffe.tudeeapp.design_system.drawables.LocalTudeeDrawables
import com.giraffe.tudeeapp.design_system.drawables.TudeeDrawables
import com.giraffe.tudeeapp.design_system.text_style.LocalTudeeTextStyle
import com.giraffe.tudeeapp.design_system.text_style.TudeeTextStyle

object Theme {
    val color: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current
    val drawables: TudeeDrawables
        @Composable @ReadOnlyComposable get() = LocalTudeeDrawables.current
}