package com.giraffe.tudeeapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBarState
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.home.composable.NoTask
import com.giraffe.tudeeapp.presentation.home.composable.OverViewSection
import com.giraffe.tudeeapp.presentation.home.composable.SliderStatus
import com.giraffe.tudeeapp.presentation.home.composable.TaskSection
import com.giraffe.tudeeapp.presentation.home.composable.TopSlider
import com.giraffe.tudeeapp.presentation.shared.taskdetails.TaskDetailsBottomSheet
import com.giraffe.tudeeapp.presentation.shared.taskeditor.TaskEditorBottomSheet
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
        onSuccessSave = {
            Log.d("TAG", "HomeScreen: ")
            viewModel.showSnackBarSuccess()
        },
        onErrorSave = {
            viewModel.showSnackBarError(it)
        },
        onThemeSwitchToggle = onThemeSwitchToggle,
        isDarkTheme = isDarkTheme
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
    onSuccessSave: () -> Unit = {},
    onErrorSave: (String?) -> Unit = {},
    onTaskClick: (Long) -> Unit = {},
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    systemUiController.setStatusBarColor(
        color = Theme.color.primary,
        darkIcons = useDarkIcons
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(127.dp)
                .background(Theme.color.primary),
        )
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

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Theme.color.surfaceHigh)
                                    .padding(top = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    TopSlider(modifier = Modifier.align(Alignment.CenterHorizontally))
                                    SliderStatus(
                                        state,
                                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                                    )
                                    OverViewSection(tasksState = state)
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Theme.color.surface)
                            ) {
                                if (state.allTasksCount == 0) {
                                    NoTask(
                                        modifier = Modifier
                                            .padding(top = 48.dp, start = 15.dp, end = 15.dp)
                                    )
                                } else {
                                    TaskSection(
                                        modifier = Modifier.padding(top = 24.dp),
                                        taskStatus = stringResource(R.string.in_progress_tasks),
                                        numberOfTasks = state.inProgressTasksCount.toString(),
                                        tasks = state.inProgressTasks,
                                        onTaskClick = onTaskClick
                                    )
                                    TaskSection(
                                        modifier = Modifier.padding(top = 24.dp),
                                        taskStatus = stringResource(R.string.to_do_tasks),
                                        numberOfTasks = state.toDoTasksCount.toString(),
                                        tasks = state.todoTasks,
                                        onTaskClick = onTaskClick
                                    )
                                    TaskSection(
                                        modifier = Modifier.padding(top = 24.dp),
                                        taskStatus = stringResource(R.string.done_tasks),
                                        numberOfTasks = state.doneTasksCount.toString(),
                                        tasks = state.doneTasks,
                                        onTaskClick = onTaskClick
                                    )
                                }
                            }
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
            key(state.currentTaskId) {
                TaskDetailsBottomSheet(
                    taskId = state.currentTaskId,
                    onnDismiss = onDismissTaskDetails,
                    onEditTask = onAddEditTask
                )
            }
        }

        var snackBarData by remember { mutableStateOf<TudeeSnackBarState?>(null) }

        if (state.isOpenAddEditTaskBottomSheet) {
            TaskEditorBottomSheet(
                taskId = state.currentTaskId,
                onDismissRequest = onDismissAddEditTask,
                modifier = Modifier.align(Alignment.BottomCenter),
                onSuccess = { message ->
                    snackBarData = TudeeSnackBarState(
                        message = message,
                        iconRes = R.drawable.ic_success,
                        isError = false
                    )
                },
                onError = { error ->
                    snackBarData = TudeeSnackBarState(
                        message = error,
                        iconRes = R.drawable.ic_error,
                        isError = true
                    )
                }
            )
        }

        snackBarData?.let {
            TudeeSnackBar(
                message = it.message,
                iconRes = it.iconRes,
                iconTintColor = if (it.isError) Theme.color.error else Theme.color.greenAccent,
                iconBackgroundColor = if (it.isError) Theme.color.errorVariant else Theme.color.greenVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Home() {
    HomeContent(state = HomeUiState())
}