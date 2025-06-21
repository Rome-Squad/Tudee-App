package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.home.HomeUiState

data class TaskCardData(
    val color: Color,
    val icon: Int,
    val taskCount: Int,
    val taskStatus: String
)

@Composable
fun OverViewSection(modifier: Modifier = Modifier, tasksState: HomeUiState) {
    TitleOverView()

    val cardsData = listOf(
        TaskCardData(
            color = Theme.color.greenAccent,
            icon = R.drawable.done_task_card_icon,
            taskCount = tasksState.tasks[TaskStatus.TODO]!!.size,
            taskStatus = stringResource(R.string.done_tasks),
        ),
        TaskCardData(
            color = Theme.color.yellowAccent,
            icon = R.drawable.in_progress_task_card_icon,
            taskCount = tasksState.tasks[TaskStatus.IN_PROGRESS]!!.size,
            taskStatus = stringResource(R.string.in_progress_tasks)
        ),
        TaskCardData(
            color = Theme.color.purpleAccent,
            icon = R.drawable.to_do_task_card_icon,
            taskCount = tasksState.tasks[TaskStatus.IN_PROGRESS]!!.size,
            taskStatus = stringResource(R.string.to_do_tasks)
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        cardsData.forEach { cardData ->
            CardOverView(
                modifier = Modifier.weight(1f),
                color = cardData.color,
                icon = painterResource(cardData.icon),
                taskCount = cardData.taskCount.toString(),
                taskStatus = cardData.taskStatus
            )
        }
    }
}