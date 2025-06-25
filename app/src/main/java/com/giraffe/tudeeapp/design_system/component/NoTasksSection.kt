package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun NoTasksSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null
) {
    val layoutDirection = LocalLayoutDirection.current
    val shouldFlip = layoutDirection == LayoutDirection.Rtl
    Row(
        modifier = modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .width(203.dp)
                .align(Alignment.Top)
                .offset(x = 15.dp, y = (-18).dp)
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
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title ?: stringResource(R.string.no_tasks_here),
                style = Theme.textStyle.title.small,
                color = Theme.color.body
            )
            Text(
                text = description ?: stringResource(R.string.tap_the_button_to_add_your_first_one),
                style = Theme.textStyle.body.small,
                color = Theme.color.hint
            )
        }

        Image(
            modifier = Modifier
                .offset(x = (-15).dp)
                .graphicsLayer {
                    scaleX = if (shouldFlip) -1f else 1f
                },
            painter = painterResource(Theme.drawables.emptyListRobot),
            contentDescription = stringResource(R.string.empty_list_robot)
        )
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun NoTasksSectionPreview() {
    TudeeTheme(isDarkTheme = true) {
        NoTasksSection()
    }
}