package com.giraffe.tudeeapp.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TudeeAppBar
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBarState
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.home.composable.OverViewSection
import com.giraffe.tudeeapp.presentation.home.composable.SliderStatus
import com.giraffe.tudeeapp.presentation.home.composable.TaskSection
import com.giraffe.tudeeapp.presentation.home.composable.TopSlider
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsBottomSheet
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorBottomSheet
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    isDarkTheme: Boolean = false,
    onThemeSwitchToggle: () -> Unit = {},
    navigateToTasksScreen: (tabIndex: Int) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.homeUiState.collectAsState()
    var snackBarData by remember { mutableStateOf<TudeeSnackBarState?>(null) }

    EventListener(
        events = viewModel.events
    ) { event ->
        when (event) {
            is HomeEvent.Error -> {
                snackBarData = TudeeSnackBarState(
                    message = context.errorToMessage(event.error),
                    isError = true
                )
            }

            is HomeEvent.NavigateToTasksScreen -> {
                navigateToTasksScreen(event.tabIndex)
            }
        }
    }

    HomeContent(
        state = state,
        snackBarState = snackBarData,
        onThemeSwitchToggle = onThemeSwitchToggle,
        isDarkTheme = isDarkTheme,
        onChangeSnackBarState = {
            snackBarData = it
        },
        actions = viewModel
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    isDarkTheme: Boolean = false,
    onThemeSwitchToggle: () -> Unit = {},
    snackBarState: TudeeSnackBarState?,
    onChangeSnackBarState: (TudeeSnackBarState?) -> Unit,
    actions: HomeActions,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .systemBarsPadding()
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
                                    .background(Theme.color.surface),
                                verticalArrangement = Arrangement.spacedBy(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (state.tasks.values.flatten().isEmpty()) {
                                    NoTasksSection(
                                        modifier = Modifier
                                            .padding(top = 48.dp, start = 15.dp, end = 15.dp)
                                    )
                                } else {
                                    TaskSection(
                                        modifier = Modifier.padding(top = 24.dp),
                                        taskStatus = stringResource(R.string.in_progress_tasks),
                                        numberOfTasks = state.tasks[TaskStatus.IN_PROGRESS]!!.size.toString(),
                                        tasks = state.tasks[TaskStatus.IN_PROGRESS]!!,
                                        onTasksLinkClick = { actions.onTasksLinkClick(1) },
                                        onTaskClick = actions::onTaskClick
                                    )
                                    TaskSection(
                                        taskStatus = stringResource(R.string.to_do_tasks),
                                        numberOfTasks = state.tasks[TaskStatus.TODO]!!.size.toString(),
                                        tasks = state.tasks[TaskStatus.TODO]!!,
                                        onTasksLinkClick = { actions.onTasksLinkClick(0) },
                                        onTaskClick = actions::onTaskClick
                                    )
                                    TaskSection(
                                        taskStatus = stringResource(R.string.done_tasks),
                                        numberOfTasks = state.tasks[TaskStatus.DONE]!!.size.toString(),
                                        tasks = state.tasks[TaskStatus.DONE]!!,
                                        onTasksLinkClick = { actions.onTasksLinkClick(2) },
                                        onTaskClick = actions::onTaskClick
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
            onClick = actions::onAddTaskClick
        )


        if (state.isTaskDetailsVisible && state.currentTaskId != null) {
            TaskDetailsBottomSheet(
                taskId = state.currentTaskId,
                onnDismiss = actions::dismissTaskDetails,
                onEditTask = actions::onEditTaskClick
            )
        }

        if (state.isTaskEditorVisible) {
            TaskEditorBottomSheet(
                taskId = state.currentTaskId,
                onDismissRequest = actions::dismissTaskEditor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(1 - (80.dp / screenHeight.dp)),
                onSuccess = { message ->
                    onChangeSnackBarState(TudeeSnackBarState(message = message, isError = false))
                },
                onError = { error ->
                    onChangeSnackBarState(TudeeSnackBarState(message = error, isError = true))
                }
            )
        }

        snackBarState?.let {
            TudeeSnackBar(
                message = it.message,
                iconRes = if (it.isError) R.drawable.ic_error else R.drawable.ic_success,
                iconTintColor = if (it.isError) Theme.color.error else Theme.color.greenAccent,
                iconBackgroundColor = if (it.isError) Theme.color.errorVariant else Theme.color.greenVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                onDismiss = { onChangeSnackBarState(null) }
            )
        }
    }
}