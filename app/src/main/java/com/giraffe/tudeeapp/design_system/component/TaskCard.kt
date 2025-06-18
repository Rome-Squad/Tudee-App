package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority

@Composable
fun TaskCard(
    taskIcon: Painter,
    blurColor: Color,
    priority: TaskPriority,
    taskTitle: String,
    taskDescription: String,
    taskCardType: TaskCardType,
    date: String? = null,
    modifier: Modifier = Modifier,
) {
    val bottomPadding = if (taskCardType == TaskCardType.TASK) 24 else 12
    Column(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.surfaceHigh)
            .padding(
                top = 4.dp, start = 4.dp, end = 12.dp, bottom = bottomPadding.dp
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
                    contentDescription = stringResource(R.string.task_icon),
                    Modifier.align(Alignment.Center)
                )
            }
            Row(
                Modifier.align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (date != null) {
                    LabelIconBox(
                        backgroundColor = Theme.color.surface,
                        contentColor = Theme.color.body,
                        icon = painterResource(R.drawable.calendar_icon),
                        label = date
                    )
                }
                when (priority) {
                    TaskPriority.HIGH -> Priority(
                        priorityType = TaskPriority.HIGH,
                        isSelected = true
                    )

                    TaskPriority.MEDIUM -> Priority(
                        priorityType = TaskPriority.MEDIUM,
                        isSelected = true
                    )

                    TaskPriority.LOW -> Priority(
                        priorityType = TaskPriority.HIGH,
                        isSelected = true
                    )
                }
            }


        }
        Text(
            text = taskTitle,
            style = Theme.textStyle.label.large,
            color = Theme.color.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 12.dp)
        )
        Text(
            text = taskDescription,
            style = Theme.textStyle.label.small,
            color = Theme.color.hint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 12.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    Column() {
        TaskCard(
            taskIcon = painterResource(R.drawable.birthday_cake_icon),
            blurColor = Theme.color.pinkAccent.copy(alpha = .08f),
            priority = TaskPriority.HIGH,
            taskTitle = "Organize Study Desk",
            taskDescription = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            taskCardType = TaskCardType.CATEGORY
        )
    }
}

enum class TaskCardType {
    TASK,
    CATEGORY
}