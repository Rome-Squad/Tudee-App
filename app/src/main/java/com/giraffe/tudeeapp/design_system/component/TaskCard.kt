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
    modifier: Modifier = Modifier,
    taskIcon: Painter,
    categoryName: String = "",
    priority: TaskPriority,
    taskTitle: String,
    taskDescription: String,
    taskCardType: TaskCardType,
    date: String? = null,
) {
    val bottomPadding = if (taskCardType == TaskCardType.TASK) 24 else 12
    val blurColor = getColorForCategoryIcon(categoryName).copy(alpha = .08f)
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
            ) {
                Image(
                    painter = taskIcon,
                    contentDescription = stringResource(R.string.task_icon),
                    Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
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
                        priorityType = TaskPriority.LOW,
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

@Composable
fun getColorForCategoryIcon(categoryName: String): Color {
    return when (categoryName) {
        "Education" -> Theme.color.purpleAccent
        "Adoration" -> Theme.color.primary
        "Family & friend" -> Theme.color.secondary
        "Cooking" -> Theme.color.pinkAccent
        "Traveling" -> Theme.color.yellowAccent
        "Coding" -> Theme.color.purpleAccent
        "Fixing bugs" -> Theme.color.pinkAccent
        "Medical" -> Theme.color.primary
        "Shopping" -> Theme.color.secondary
        "Agriculture" -> Theme.color.greenAccent
        "Entertainment" -> Theme.color.yellowAccent
        "Gym" -> Theme.color.primary
        "Cleaning" -> Theme.color.greenAccent
        "Work" -> Theme.color.secondary
        "Event" -> Theme.color.pinkAccent
        "Budgeting" -> Theme.color.purpleAccent
        "Self-care" -> Theme.color.yellowAccent
        else -> Theme.color.purpleAccent
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    Column {
        TaskCard(
            taskIcon = painterResource(R.drawable.birthday_cake_icon),
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