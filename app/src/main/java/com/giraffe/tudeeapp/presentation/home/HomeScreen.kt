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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.DefaultSnackBar
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TudeeAppBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.presentation.home.composable.OverViewSection
import com.giraffe.tudeeapp.presentation.home.composable.SliderStatus
import com.giraffe.tudeeapp.presentation.home.composable.TaskSection
import com.giraffe.tudeeapp.presentation.home.composable.TopSlider
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsBottomSheet
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorBottomSheet
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.convertToArabicNumbers
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import com.giraffe.tudeeapp.presentation.utils.showErrorSnackbar
import com.giraffe.tudeeapp.presentation.utils.showSuccessSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navigateToTasksScreen: (tabIndex: Int) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    EventListener(
        events = viewModel.effect
    ) { event ->
        when (event) {
            is HomeScreenEffect.Error -> {
                snackBarHostState.showErrorSnackbar(context.errorToMessage(event.error))
            }

            is HomeScreenEffect.NavigateToTasksScreen -> {
                navigateToTasksScreen(event.tabIndex)
            }
        }
    }

    HomeContent(
        state = state,
        actions = viewModel,
        snackBarHostState = snackBarHostState,
        showSnackBar = { message, isError ->
            scope.launch {
                if (isError) {
                    snackBarHostState.showErrorSnackbar(message)
                } else {
                    snackBarHostState.showSuccessSnackbar(message)
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeScreenState,
    snackBarHostState: SnackbarHostState,
    showSnackBar: (String, Boolean) -> Unit = { message, isError -> },
    actions: HomeScreenInteractionListener,
) {
    val screenSize = LocalWindowInfo.current.containerSize
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.primary)
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .background(Theme.color.surface)
        ) {
            TudeeAppBar(
                isDarkTheme = state.isDarkTheme,
                onThemeSwitchToggle = actions::onToggleTheme
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Theme.color.primary)
                        .align(Alignment.TopCenter)
                )
                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Theme.color.surfaceHigh)
                                .padding(top = 8.dp)
                        ) {
                            TopSlider(modifier = Modifier.align(Alignment.CenterHorizontally))
                            SliderStatus(
                                state,
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                            )
                            OverViewSection(tasksState = state)
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Theme.color.surface)
                                .padding(top = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (state.tasks.values.flatten().isEmpty()) {
                                NoTasksSection(
                                    modifier = Modifier
                                        .padding(top = 74.dp, start = 15.dp, end = 15.dp)
                                )
                            }

                            if (state.tasks[TaskStatus.IN_PROGRESS]!!.isNotEmpty()) {
                                TaskSection(
                                    taskStatus = stringResource(R.string.in_progress_tasks),
                                    numberOfTasks = convertToArabicNumbers(state.tasks[TaskStatus.IN_PROGRESS]!!.size.toString()),
                                    tasks = state.tasks[TaskStatus.IN_PROGRESS]!!,
                                    onTasksLinkClick = { actions.onTasksLinkClick(1) },
                                    onTaskClick = actions::onTaskClick
                                )
                            }

                            if (state.tasks[TaskStatus.TODO]!!.isNotEmpty()) {
                                TaskSection(
                                    taskStatus = stringResource(R.string.to_do_tasks),
                                    numberOfTasks = convertToArabicNumbers(state.tasks[TaskStatus.TODO]!!.size.toString()),
                                    tasks = state.tasks[TaskStatus.TODO]!!,
                                    onTasksLinkClick = { actions.onTasksLinkClick(0) },
                                    onTaskClick = actions::onTaskClick
                                )
                            }

                            if (state.tasks[TaskStatus.DONE]!!.isNotEmpty()) {
                                TaskSection(
                                    taskStatus = stringResource(R.string.done_tasks),
                                    numberOfTasks = convertToArabicNumbers(state.tasks[TaskStatus.DONE]!!.size.toString()),
                                    tasks = state.tasks[TaskStatus.DONE]!!,
                                    onTasksLinkClick = { actions.onTasksLinkClick(2) },
                                    onTaskClick = actions::onTaskClick
                                )
                            }
                        }
                    }
                }
                FabButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    icon = painterResource(R.drawable.add_task),
                    onClick = actions::onAddTaskClick
                )

                if (state.isTaskDetailsVisible && state.currentTaskId != null) {
                    TaskDetailsBottomSheet(
                        taskId = state.currentTaskId,
                        onnDismiss = actions::onDismissTaskDetailsRequest,
                        onEditTask = actions::onEditTaskClick
                    )
                }

                if (state.isTaskEditorVisible) {
                    TaskEditorBottomSheet(
                        taskId = state.currentTaskId,
                        onDismissRequest = actions::onDismissTaskEditorRequest,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxHeight(1 - (80.dp / screenSize.height.dp)),
                        onSuccess = { message ->
                            showSnackBar(message, false)
                        },
                        onError = { error ->
                            showSnackBar(error, true)
                        }
                    )
                }

            }
        }
        DefaultSnackBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
            snackState = snackBarHostState
        )
    }

}