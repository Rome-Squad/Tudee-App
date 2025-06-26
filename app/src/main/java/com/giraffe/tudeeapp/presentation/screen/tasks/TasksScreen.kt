package com.giraffe.tudeeapp.presentation.screen.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.AlertBottomSheet
import com.giraffe.tudeeapp.design_system.component.DefaultSnackBar
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.screen.taskdetails.TaskDetailsBottomSheet
import com.giraffe.tudeeapp.presentation.screen.taskeditor.TaskEditorBottomSheet
import com.giraffe.tudeeapp.presentation.screen.tasks.composable.DatePicker
import com.giraffe.tudeeapp.presentation.screen.tasks.composable.SwipableTask
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import com.giraffe.tudeeapp.presentation.utils.showErrorSnackbar
import com.giraffe.tudeeapp.presentation.utils.showSuccessSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    EventListener(
        events = viewModel.effect
    ) {
        when (it) {
            is TasksScreenEffect.Error -> {
                snackBarHostState.showErrorSnackbar(context.errorToMessage(it.error))
            }

            TasksScreenEffect.TaskDeletedSuccess -> {
                snackBarHostState.showSuccessSnackbar(context.getString(R.string.deleted_task_successfully))
            }

            is TasksScreenEffect.OpenTaskEditor -> {
                viewModel.setTaskEditorDate(it.selectedDate)
                viewModel.showTaskEditor()
            }
        }
    }

    TaskScreenContent(
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
fun TaskScreenContent(
    state: TasksScreenState = TasksScreenState(),
    actions: TasksScreenInteractionListener,
    snackBarHostState: SnackbarHostState,
    showSnackBar: (String, Boolean) -> Unit = { message, isError -> },
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = Theme.textStyle.title.large,
                color = Theme.color.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.color.surfaceHigh)
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            )

            DatePicker(onDateSelected = actions::setSelectedDate, selectedDate = state.selectedDate)
            TabsBar(
                startTab = state.selectedTab,
                onTabSelected = actions::setSelectedTab,
                tasks = state.tasks.mapValues { (_, value) -> value.size }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
            ) {
                val selectedTasks = state.tasks[state.selectedTab] ?: emptyList()
                if (selectedTasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            NoTasksSection()
                        }
                    }
                } else {
                    itemsIndexed(selectedTasks) { index, task ->
                        SwipableTask(
                            task = task,
                            action = {
                                actions.setSelectedTaskId(task.id)
                                actions.onDeleteTaskClick()
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    actions.onTaskClick(task.id)
                                },
                            onCollapsed = {
                                actions.setSelectedTaskId(task.id)
                                actions.onDeleteTaskClick()
                            }
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
            onClick = { actions.onAddTaskClick() }
        )

        AlertBottomSheet(
            title = stringResource(R.string.delete_task),
            subTitle = stringResource(R.string.are_you_sure_to_continue),
            image = painterResource(R.drawable.sure_robot),
            positiveButtonTitle = stringResource(R.string.delete),
            negativeButtonTitle = stringResource(R.string.cancel),
            isVisible = state.isDeleteTaskBottomSheetVisible,
            onRedBtnClick = {
                actions.onConfirmDeleteTask(state.selectedTaskId)
            },
            onBlueBtnClick = { actions.onDismissDeleteTaskBottomSheetRequest() },
            onVisibilityChange = {
                if (it) {
                    actions.onDeleteTaskClick()
                } else {
                    actions.onDismissDeleteTaskBottomSheetRequest()
                }
            }
        )

        if (state.isTaskDetailsBottomSheetVisible && state.currentTaskId != null) {
            TaskDetailsBottomSheet(
                taskId = state.currentTaskId,
                onnDismiss = actions::onDismissTaskDetailsBottomSheetRequest,
                onEditTask = actions::onEditTaskClick
            )

        }

        if (state.isTaskEditorBottomSheetVisible) {
            TaskEditorBottomSheet(
                taskId = state.currentTaskId,
                onDismissRequest = actions::onDismissTaskEditorBottomSheetRequest,
                modifier = Modifier.align(Alignment.BottomCenter),
                selectedDate = state.selectedDate,
                onSuccess = { message ->
                    showSnackBar(message, false)
                },
                onError = { error ->
                    showSnackBar(error, true)
                }
            )
        }

        DefaultSnackBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp),
            snackState = snackBarHostState,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TudeeTheme(isDarkTheme = true) {
        TaskScreen()
    }
}