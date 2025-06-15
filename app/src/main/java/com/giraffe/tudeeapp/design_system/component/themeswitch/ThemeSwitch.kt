package com.giraffe.tudeeapp.design_system.component.themeswitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.component.themeswitch.components.Moon
import com.giraffe.tudeeapp.design_system.component.themeswitch.components.NightSkyBackground
import com.thechance.weatherapp.core.ui.themeswitch.components.Clouds

@Composable
fun ThemeSwitch(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    switchSize: Dp = 150.dp,
    padding: Dp = 4.dp,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 1000),
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize / 2 else 0.dp,
        animationSpec = animationSpec
    )

    Box(
        modifier = modifier
            .width(switchSize)
            .height(switchSize / 2)
            .clip(shape = CircleShape)
            .clickable { onClick() }
    ) {
        AnimatedVisibility(
            visible = isDarkTheme,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            NightSkyBackground(
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        AnimatedVisibility(
            visible = !isDarkTheme,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0xFF548EFE)
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(switchSize / 2)
                .offset(x = offset)
                .padding(padding)
                .clip(shape = CircleShape)
                .shadow(
                    elevation = switchSize,
                    shape = CircleShape,
                    spotColor = Color(0xFFF2C849),
                    ambientColor = Color(0xFFF49061)
                )
                .background(
                    Brush.linearGradient(
                        colors = if (isDarkTheme) {
                            listOf(
                                Color(0xFFE8E8E8),
                                Color(0xFFE8E8E8)
                            )
                        } else {
                            listOf(
                                Color(0xEFFFCC33),
                                Color(0xEFFC9601)
                            )
                        },
                        start = Offset(0f, 0f),
                        end = Offset(switchSize.value / 1.5f, switchSize.value / 1.5f)
                    )

                )
        ) {

            AnimatedVisibility(
                visible = isDarkTheme,
                enter = fadeIn(),
                exit = fadeOut() + scaleOut(),
                modifier = Modifier
                    .shadow(
                        elevation = switchSize,
                        shape = CircleShape,
                        spotColor = Color(0xFF323297),
                        ambientColor = Color(0xFF232357)
                    )
            ) {
                Moon(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }


        }

        AnimatedVisibility(
            visible = !isDarkTheme,
            enter = fadeIn() + scaleIn(
                initialScale = 3f,
                animationSpec = tween(durationMillis = 300)
            ),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .offset(x = switchSize * 0.1f)
        ) {
            Clouds(
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }
}

@Preview(showBackground = false)
@Composable
fun ThemeSwitcherPreview() {
    Column {
        ThemeSwitch(
            isDarkTheme = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(50.dp))
        ThemeSwitch(
            isDarkTheme = true,
            onClick = {}
        )
    }
}