package com.giraffe.tudeeapp.design_system.drawables

import androidx.compose.runtime.staticCompositionLocalOf
import com.giraffe.tudeeapp.R

data class TudeeDrawables(
    val logoImageResId: Int,
    val bacgroundImage: Int,
    val emptyListRobot: Int
)

val lightDrawables = TudeeDrawables(
    logoImageResId = R.drawable.logo_light,
    bacgroundImage = R.drawable.background_light,
    emptyListRobot = R.drawable.empty_list_robot
)

val darkDrawables = TudeeDrawables(
    logoImageResId = R.drawable.logo_dark,
    bacgroundImage = R.drawable.background_dark,
    emptyListRobot = R.drawable.dark_empty_list_robot

)

val LocalTudeeDrawables = staticCompositionLocalOf { lightDrawables }