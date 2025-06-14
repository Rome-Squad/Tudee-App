package com.giraffe.tudeeapp.design_system.component.themeswitch.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random

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

@Preview(showBackground = true)
@Composable
fun NightSkyBackgroundPreview() {
    NightSkyBackground(
        modifier = Modifier.fillMaxSize(),
    )
}