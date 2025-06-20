package com.giraffe.tudeeapp.design_system.resources

import androidx.compose.runtime.staticCompositionLocalOf
import com.giraffe.tudeeapp.R

data class TudeeDrawable(
    val logoImageResId: Int,
    val bacgroundImage: Int
)

val lightResources = TudeeDrawable(
    logoImageResId = R.drawable.logo_light,
    bacgroundImage = R.drawable.background_light
)

val darkResources = TudeeDrawable(
    logoImageResId = R.drawable.logo_dark,
    bacgroundImage = R.drawable.background_dark

)

val LocalTudeeResources = staticCompositionLocalOf { lightResources }