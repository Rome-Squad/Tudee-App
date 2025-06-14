package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle

@Composable
fun TaskCard(
    taskIcon: Painter,
    blurColor: Color,
    priority: PriorityType,
    taskTitle: String,
    taskDescription: String,
    date: String = "12-03-2025",
    withDate: Boolean = false
) {
    Box(Modifier.clip(RoundedCornerShape(16.dp))) {
        Column {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 4.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    Modifier
                        .size(56.dp)
                        .graphicsLayer {
                            spotShadowColor = blurColor
                            ambientShadowColor = blurColor
                            shadowElevation = with(density) { 50.dp.toPx() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = taskIcon,
                        contentDescription = "Task Icon",
                        Modifier.align(Alignment.Center)
                    )
                }
                Row {
                    if (withDate) {
                        BaseBox(
                            backgroundColor = LocalTudeeColors.current.surface,
                            contentColor = LocalTudeeColors.current.body,
                            icon = painterResource(R.drawable.calendar_icon),
                            label = date
                        )
                        Spacer(Modifier.width(4.dp))
                    }
                    when (priority) {
                        PriorityType.HIGH -> HighPriority(isSelected = true)
                        PriorityType.MEDIUM -> MediumPriority(isSelected = true)
                        PriorityType.LOW -> LowPriority(isSelected = true)
                    }
                }


            }
            Text(
                text = taskTitle,
                style = defaultTextStyle.label.large,
                color = LocalTudeeColors.current.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, end = 12.dp)
            )
            Text(
                text = taskDescription,
                style = defaultTextStyle.label.small,
                color = LocalTudeeColors.current.hint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, bottom = 12.dp, end = 12.dp)
            )
        }
    }
}

@Composable
fun TaskCardWithoutDate(
    taskIcon: Painter,
    blurColor: Color,
    priority: PriorityType,
    taskTitle: String,
    taskDescription: String,
) {
    TaskCard(
        taskIcon = taskIcon,
        blurColor = blurColor,
        priority = priority,
        taskTitle = taskTitle,
        taskDescription = taskDescription,
    )
}

@Composable
fun TaskCardWithDate(
    taskIcon: Painter,
    blurColor: Color,
    priority: PriorityType,
    taskTitle: String,
    taskDescription: String,
    date: String
) {
    TaskCard(
        taskIcon = taskIcon,
        blurColor = blurColor,
        priority = priority,
        taskTitle = taskTitle,
        taskDescription = taskDescription,
        withDate = true,
        date = date
    )
}


@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    Column {
        TaskCardWithDate(
            taskIcon = painterResource(R.drawable.birthday_cake_icon),
            blurColor = LocalTudeeColors.current.pinkAccent.copy(alpha = .08f),
            priority = PriorityType.HIGH,
            taskTitle = "Organize Study Desk",
            taskDescription = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025"
        )
        Spacer(Modifier.height(20.dp))
        TaskCardWithoutDate(
            taskIcon = painterResource(R.drawable.birthday_cake_icon),
            blurColor = LocalTudeeColors.current.pinkAccent.copy(alpha = .08f),
            priority = PriorityType.HIGH,
            taskTitle = "Organize Study Desk",
            taskDescription = "Review cell structure and functions for tomorrow...",
        )
    }
}