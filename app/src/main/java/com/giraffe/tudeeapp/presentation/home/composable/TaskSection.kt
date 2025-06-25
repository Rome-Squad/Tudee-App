package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.Task

@Composable
fun TaskSection(
    modifier: Modifier = Modifier,
    taskStatus: String = stringResource(R.string.in_progress_tasks),
    numberOfTasks: String = "0",
    tasks: List<Task> = emptyList(),
    onTaskClick: (Long) -> Unit,
    onTasksLinkClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskStatus,
                color = Theme.color.title,
                style = Theme.textStyle.title.large
            )
            Box(
                modifier = Modifier
                    .background(
                        color = Theme.color.surfaceHigh,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clip(RoundedCornerShape(100.dp))
                    .clickable {
                        onTasksLinkClick()
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = numberOfTasks,
                        color = Theme.color.body,
                        style = Theme.textStyle.label.small
                    )
                    Icon(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "arrow icon",
                        tint = Theme.color.body,
                    )
                }
            }
        }

        tasks.takeLast(2).reversed().forEach { task ->
            TaskCard(
                isDateVisible = false,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onTaskClick(task.id) },
                task = task,
            )
        }
    }
}

