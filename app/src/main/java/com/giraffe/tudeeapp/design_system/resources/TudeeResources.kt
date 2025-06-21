package com.giraffe.tudeeapp.design_system.resources

import androidx.compose.runtime.staticCompositionLocalOf
import com.giraffe.tudeeapp.R

data class TudeeResources(
    val logoImageResId: Int,
    val bacgroundImage: Int,
    val emptyListRobot: Int
)

val lightResources = TudeeResources(
    logoImageResId = R.drawable.logo_light,
    bacgroundImage = R.drawable.background_light,
    emptyListRobot = R.drawable.empty_list_robot
)

val darkResources = TudeeResources(
    logoImageResId = R.drawable.logo_dark,
    bacgroundImage = R.drawable.background_dark,
    emptyListRobot = R.drawable.dark_empty_list_robot

)

val LocalTudeeResources = staticCompositionLocalOf { lightResources }