package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun NoTasksSection(
    modifier: Modifier = Modifier,
    title: String = "No tasks here!",
    description: String = "Tap the + button to add your first one."
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)

    ) {




        // Image Overlay Circle
        Box(
            modifier = Modifier
                .offset(x = 204.dp, y = 20.dp)
                .size(136.dp)
                .background(
                    color =Theme.color.overlay,
                    shape = CircleShape
                )
        )

        // Image Background Circle
        Box(
            modifier = Modifier
                .offset(x = 191.dp, y = 8.dp)
                .size(144.dp)
                .border(
                    width = 1.dp,
                    color = Theme.color.overlay,
                    shape = CircleShape
                )
        )


        // Ellipse Progress Indicator
        Box(
            modifier = Modifier
                .offset(x = 210.dp, y = 73.dp)
        ) {
            EllipseDot(size = 14.dp, offsetY = 0.dp)
            EllipseDot(size = 8.dp, offsetY = 17.dp)
            EllipseDot(size = 4.dp, offsetY = 30.dp)
        }

        // Oval Copy small
        Box(
            modifier = Modifier
                .offset(x = 213.dp, y = 130.dp)
                .size(14.4.dp)
                .background(Theme.color.overlay, shape = CircleShape)
                .border(1.dp, Theme.color.overlay, shape = CircleShape)
        )

        // Robot Image
        Image(
            painter = painterResource(id = R.drawable.no_tasks_robot),
            contentDescription = "No tasks robot",
            modifier = Modifier
                .offset(x = 233.dp, y = 53.dp)
                .size(width = 107.dp, height = 100.dp)
        )

        // Message Container
        Column(
            modifier = Modifier
                .offset(x = 10.dp, y = (-4).dp)
                .width(203.dp)
                .height(74.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 2.dp,
                        bottomStart = 16.dp
                    )
                )
                .background(Theme.color.surfaceHigh)
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.small
                , color = Theme.color.body

            )
            Text(
                text = description,
                style = Theme.textStyle.body.small
                , color = Theme.color.hint
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun NoTasksSectionPreview() {
    TudeeTheme {
        NoTasksSection()
    }
}
@Composable
fun EllipseDot(size: Dp, offsetY: Dp) {
    Box(
        modifier = Modifier
            .offset(y = offsetY)
            .size(size)
            .background(color = Theme.color.surfaceLow, shape = CircleShape)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor =Theme.color.surfaceHigh ,
                spotColor = Theme.color.surfaceHigh
            )
    )
}