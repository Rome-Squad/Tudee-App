package com.giraffe.tudeeapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.TudeeAppBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.home.addedittask.TaskEditorBottomSheet
import com.giraffe.tudeeapp.presentation.home.composable.NoTask
import com.giraffe.tudeeapp.presentation.home.composable.OverViewSection
import com.giraffe.tudeeapp.presentation.home.composable.SliderStatus
import com.giraffe.tudeeapp.presentation.home.composable.TaskSection
import com.giraffe.tudeeapp.presentation.home.composable.TopSlider
import com.giraffe.tudeeapp.presentation.home.taskdetails.TaskDetailsBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    isDarkTheme: Boolean = false,
    onThemeSwitchToggle: () -> Unit = {},
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.homeUiState.collectAsState()
    HomeContent(
        state = state,
        onDismissTaskDetails = viewModel::closeTaskDetails,
        onAddEditTask = {
            viewModel.openAddEditTaskBottomSheet(it)
        },
        onDismissAddEditTask = viewModel::closeAddEditTaskBottomSheet,
    ) { taskId ->
        viewModel.openTaskDetails(taskId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    isDarkTheme: Boolean = false,
    onThemeSwitchToggle: () -> Unit = {},
    onDismissTaskDetails: () -> Unit = {},
    onDismissAddEditTask: () -> Unit = {},
    onAddEditTask: (Long?) -> Unit = {},
    onTaskClick: (Long) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var width by remember { mutableIntStateOf(0) }
            val heightInDp = with(LocalDensity.current) { (width * 0.2f).toDp() }
            TudeeAppBar(
                modifier = Modifier
                    .onGloballyPositioned {
                        width = it.size.width
                    }
                    .height(heightInDp),
                isDarkTheme = isDarkTheme,
                onThemeSwitchToggle = onThemeSwitchToggle
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .background(Theme.color.primary),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Theme.color.surface)
                                .padding(top = 230.dp)
                        ) {
                            if (state.allTasksCount == 0) {
                                NoTask(
                                    modifier = Modifier
                                        .padding(top = 48.dp, start = 15.dp, end = 15.dp)
                                )
                            } else {
                                TaskSection(
                                    taskStatus = stringResource(R.string.in_progress_tasks),
                                    numberOfTasks = state.inProgressTasksCount.toString(),
                                    tasks = state.inProgressTasks,
                                    onTaskClick = onTaskClick
                                )
                                TaskSection(
                                    taskStatus = stringResource(R.string.to_do_tasks),
                                    numberOfTasks = state.toDoTasksCount.toString(),
                                    tasks = state.todoTasks,
                                    onTaskClick = onTaskClick
                                )
                                TaskSection(
                                    taskStatus = stringResource(R.string.done_tasks),
                                    numberOfTasks = state.doneTasksCount.toString(),
                                    tasks = state.doneTasks,
                                    onTaskClick = onTaskClick
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Theme.color.surfaceHigh)
                            .padding(top = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            TopSlider(modifier = Modifier.align(Alignment.CenterHorizontally))
                            SliderStatus(
                                state,
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                            )
                            OverViewSection(tasksState = state)
                        }
                    }
                }
            }
        }
        FabButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 10.dp),
            icon = painterResource(R.drawable.add_task_icon),
            onClick = {
                onAddEditTask(null)
            }
        )
        if (state.isOpenTaskDetailsBottomSheet && state.currentTaskId != null) {
            TaskDetailsBottomSheet(
                taskId = state.currentTaskId,
                onnDismiss = onDismissTaskDetails,
                onEditTask = onAddEditTask
            )
        }

        if (state.isOpenAddEditTaskBottomSheet) {
            TaskEditorBottomSheet(
                taskId = state.currentTaskId,
                onDismissRequest = onDismissAddEditTask,
                headerTitle = if (state.currentTaskId == null) "Add Task" else "Edit Task",
                saveButtonText = "Save"
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    HomeContent(state = HomeUiState())
}