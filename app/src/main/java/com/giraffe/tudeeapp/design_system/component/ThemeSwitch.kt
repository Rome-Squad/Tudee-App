package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme
import kotlin.random.Random

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
                        Color(0xFF04B4EC)
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
                    spotColor = Color(0xFFFC9601),
                    ambientColor = Color(0xFFFC9601)
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

@Composable
fun Clouds(
    color: Color = Theme.color.surfaceHigh,
    strokeColor: Color = Theme.color.surfaceLow,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(250.dp, 300.dp)) {
        val radius = size.minDimension / 4f
        val center1 = Offset(size.width / 2f - radius / 2, size.height / 1.05f)
        val center2 = Offset(size.width / 1.15f - radius, 0f)
        val center3 = Offset(size.width / 1.1f - radius, size.height / 1.18f)

        val path1 = Path().apply {
            addOval(Rect(center1 - Offset(radius, radius), Size(radius * 4, radius * 4)))
        }

        val path2 = Path().apply {
            addOval(Rect(center2 - Offset(radius, radius), Size(radius * 5, radius * 5)))
        }

        val path3 = Path().apply {
            addOval(Rect(center3 - Offset(radius, radius), Size(radius * 3, radius * 3)))
        }

        drawPath(path1, color = color)
        drawPath(path2, color = color)

        drawPath(
            path = path1,
            color = strokeColor,
            style = Stroke(width = 24f)
        )

        drawPath(
            path = path2,
            color = strokeColor,
            style = Stroke(width = 24f)
        )

        drawPath(path3, color = Color.White)
    }
}

@Composable
fun Moon(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE0E9FE),
                        Color(0xFFE9F0FF),
                    ),
                )
            )
    ) {

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFE9EFFF),
                    Color(0xFFBFD2FF),
                ),
                center = Offset(size.width / 2f, size.height / 4f),
                radius = size.minDimension * 0.25f
            ),
            radius = size.minDimension * 0.15f,
            center = Offset(size.width * 0.4f, size.height * 0.2f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFE9EFFF),
                    Color(0xFFBFD2FF),
                ),
                center = Offset(size.width / 1.8f, size.height / 1.4f),
                radius = size.minDimension * .6f
            ),
            radius = size.minDimension * 0.25f,
            center = Offset(size.width * 0.35f, size.height * 0.64f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFE9EFFF),
                    Color(0xFFBFD2FF),
                ),
                center = Offset(size.width / 1.3f, size.height / 1f),
            ),
            radius = size.minDimension * 0.08f,
            center = Offset(size.width * 0.7f, size.height * 0.82f)
        )
    }
}

@Composable
fun NightSkyBackground(
    modifier: Modifier = Modifier,
    starCount: Int = 50 // Adjust number of stars
) {
    val stars = remember {
        List(starCount) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 2 + 1f
            )
        }
    }

    Canvas(modifier = modifier.background(Color(0xFF151535))) {
        val width = size.width
        val height = size.height

        stars.forEach { star ->
            drawCircle(
                color = Color.White,
                radius = star.radius,
                center = Offset(star.x * width, star.y * height)
            )
        }
    }
}

data class Star(
    val x: Float,
    val y: Float,
    val radius: Float
)

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