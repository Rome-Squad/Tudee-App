package com.thechance.weatherapp.core.ui.themeswitch.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun Clouds(
    color: Color = Theme.color.surfaceHigh,
    strokeColor: Color = Theme.color.surfaceLow,
    modifier: Modifier = Modifier) {
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


@Preview(showBackground = true)
@Composable
fun CloudsPreview() {
    Clouds(
        modifier = Modifier.fillMaxSize()
    )
}