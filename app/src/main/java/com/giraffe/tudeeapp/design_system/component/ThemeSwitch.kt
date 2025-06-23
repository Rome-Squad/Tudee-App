package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
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

    val moonGradientFirstColor by animateColorAsState(
        targetValue = if (isDarkTheme) Color(0xFFE9EFFF) else Color.White
    )

    val moonGradientSecondColor by animateColorAsState(
        targetValue = if (isDarkTheme) Color(0xFFBFD2FF) else Color.White
    )

    val mediumCircleSize by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .1f else switchSize * .38f,
        animationSpec = animationSpec
    )

    val mediumCircleOffsetX by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .65f else switchSize * .78f,
        animationSpec = animationSpec
    )

    val mediumCircleOffsetY by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .055f else 0.dp,
        animationSpec = animationSpec
    )

    val largeCircleSize by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .21f else switchSize * .25f,
        animationSpec = animationSpec
    )

    val largeCircleOffsetX by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .58f else switchSize * .57f,
        animationSpec = animationSpec
    )

    val largeCircleOffsetY by animateDpAsState(
        targetValue = if (isDarkTheme) switchSize * .17f else switchSize * .35f,
        animationSpec = animationSpec
    )

    val indicatorOffset by animateDpAsState(
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
                .offset(x = indicatorOffset)
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
                                Color(0xFFE9F0FF),
                                Color(0xFFE0E9FE)
                            )
                        } else {
                            listOf(
                                Color(0xEFFFCC33),
                                Color(0xEFFC9601)
                            )
                        },
                        start = Offset(0f, switchSize.value / 4f),
                        end = Offset(switchSize.value / 2f, switchSize.value / 4f),
                    )
                )
        )

        AnimatedVisibility(
            visible = !isDarkTheme,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(switchSize * .5f)
                    .offset(
                        x = switchSize * .7f,
                        y = switchSize * -.05f
                    )
                    .clip(CircleShape)
                    .background(
                        color = Color(0xFFE0E0E0)
                    )
            )

            Box(
                modifier = Modifier
                    .size(switchSize * .35f)
                    .offset(
                        x = switchSize * .5f,
                        y = switchSize * .28f
                    )
                    .clip(CircleShape)
                    .background(
                        color = Color(0xFFE0E0E0)
                    )
            )
        }


        AnimatedVisibility(
            visible = !isDarkTheme,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(switchSize * .25f)
                    .offset(
                        x = switchSize * .79f,
                        y = switchSize * .34f
                    )
                    .clip(CircleShape)
                    .background(
                        color = Color.White
                    )
            )
        }

        AnimatedVisibility(
            visible = isDarkTheme,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(switchSize * .06f)
                    .offset(
                        x = switchSize * .78f,
                        y = switchSize * .36f
                    )
                    .clip(CircleShape)
                    .background(
                        color = Color(0xFFBFD2FF)
                    )
            )
        }


        Box(
            modifier = Modifier
                .size(largeCircleSize)
                .offset(
                    x = largeCircleOffsetX,
                    y = largeCircleOffsetY
                )
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            moonGradientFirstColor,
                            moonGradientSecondColor
                        ),
                        center = Offset(switchSize.value * .4f, switchSize.value * .36f),
                        radius = switchSize.value / 2f,
                        tileMode = TileMode.Mirror
                    )
                )
        )


        Box(
            modifier = Modifier
                .size(mediumCircleSize)
                .offset(
                    x = mediumCircleOffsetX,
                    y = mediumCircleOffsetY
                )
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            moonGradientFirstColor,
                            moonGradientSecondColor
                        ),
                        center = Offset(switchSize.value * .4f, switchSize.value * .36f),
                        radius = switchSize.value / 2f,
                        tileMode = TileMode.Mirror
                    )
                )
        )

    }
}

@Composable
fun NightSkyBackground(
    modifier: Modifier = Modifier,
    starCount: Int = 25 // Adjust number of stars
) {
    val stars = remember {
        List(starCount) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 5 + 1f
            )
        }
    }

    Canvas(modifier = modifier.background(Color(0xFF151535))) {
        val width = size.width
        val height = size.height

        stars.forEach { star ->
            drawStar(
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

fun DrawScope.drawStar(
    center: Offset,
    radius: Float,
    points: Int = 4
) {
    val path = Path()
    val angle = (2 * PI / points).toFloat()
    val innerRadius = radius / 2.5f

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else innerRadius
        val x = (center.x + r * cos(i * angle / 2)).toFloat()
        val y = (center.y + r * sin(i * angle / 2)).toFloat()

        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()

    val gradient = Brush.radialGradient(
        colors = listOf(Color.White, Color.Transparent),
        center = center,
        radius = radius
    )

    drawPath(path = path, brush = gradient)
}

@Preview(showBackground = false)
@Composable
fun ThemeSwitcherPreview() {
    var isDarkTheme by remember { mutableStateOf(true) }

        ThemeSwitch(
            isDarkTheme = isDarkTheme,
            onClick = {isDarkTheme = !isDarkTheme}
        )

}