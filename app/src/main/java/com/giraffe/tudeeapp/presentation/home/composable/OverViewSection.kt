package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.home.uistate.TasksUiState

@Composable
fun OverViewSection(modifier: Modifier = Modifier, tasksState: TasksUiState) {
    TitleOverView()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CardOverView(
            modifier = Modifier.weight(1f),
            color = Theme.color.greenAccent,
            icon = painterResource(R.drawable.done_task_card_icon),
            taskCount = tasksState.doneTasksCount.toString(),
            taskStatus = stringResource(R.string.done_tasks),
        )
        CardOverView(
            modifier = Modifier.weight(1f),
            color = Theme.color.yellowAccent,
            icon = painterResource(R.drawable.in_progress_task_card_icon),
            taskCount = tasksState.inProgressTasksCount.toString(),
            taskStatus = stringResource(R.string.in_progress_tasks)
        )
        CardOverView(
            modifier = Modifier.weight(1f),
            color = Theme.color.purpleAccent,
            icon = painterResource(R.drawable.to_do_task_card_icon),
            taskCount = tasksState.toDoTasksCount.toString(),
            taskStatus = stringResource(R.string.to_do_tasks)
        )
    }
}