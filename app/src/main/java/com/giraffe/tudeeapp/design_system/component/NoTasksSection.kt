package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun NoTasksSection(
    modifier: Modifier = Modifier,
    title: String = "No tasks here!",
    description: String = "Tap the + button to add your first one."
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .width(203.dp)
                .offset(x = 16.dp)
                .zIndex(1f)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 2.dp,
                        bottomStart = 16.dp
                    ),
                    ambientColor = Color.Black.copy(.4f),
                    spotColor = Color.Black.copy(.4f),
                )
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
                style = Theme.textStyle.title.small, color = Theme.color.body

            )
            Text(
                text = description,
                style = Theme.textStyle.body.small, color = Theme.color.hint
            )
        }
        RobotWithCircles(modifier = Modifier.padding(top = 35.dp))
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
fun RobotWithCircles(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(136.dp)
                .background(
                    color = Theme.color.overlay,
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(144.dp)
                .border(
                    width = 1.dp,
                    color = Theme.color.overlay,
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            EllipseDot(modifier = Modifier.padding(horizontal = 8.dp), size = 14.dp)
            EllipseDot(modifier = Modifier.padding(horizontal = 4.dp), size = 8.dp)
            EllipseDot(modifier = Modifier, size = 4.dp)
        }

        Box(
            modifier = Modifier
                .size(14.4.dp)
                .background(Theme.color.overlay, shape = CircleShape)
                .border(1.dp, Theme.color.overlay, shape = CircleShape)
        )

        Image(
            painter = painterResource(id = R.drawable.no_tasks_robot),
            contentDescription = "No tasks robot",
            modifier = Modifier
                .size(width = 107.dp, height = 100.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun EllipseDot(
    modifier: Modifier,
    size: Dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = Theme.color.surfaceHigh, shape = CircleShape)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor = Theme.color.surfaceHigh,
                spotColor = Theme.color.surfaceHigh
            )
    )
}