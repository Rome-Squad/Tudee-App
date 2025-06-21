package com.giraffe.tudeeapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.AlertBottomSheet
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsBottomSheet
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorBottomSheet
import com.giraffe.tudeeapp.presentation.tasks.components.DatePicker
import com.giraffe.tudeeapp.presentation.tasks.components.SwipableTask
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    TaskScreenContent(
        state = state,
        actions = viewModel,

    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenContent(
    state: TasksScreenState = TasksScreenState(),
    actions: TasksScreenActions,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderContent(stringResource(R.string.tasks))

            DatePicker(actions::setPickedDate)
            TabsBar(
                startTab = state.selectedTab,
                onTabSelected = actions::selectTab,
                tasks = state.tasks.mapValues { (_, value) -> value.size }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                val selectedTasks = state.tasks[state.selectedTab] ?: emptyList()
                if (selectedTasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(start = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            NoTasksSection()
                        }
                    }
                } else {
                    itemsIndexed(selectedTasks) { index, taskUi ->
                        SwipableTask(
                            taskUi = taskUi,
                            action = {
                                actions.setDeleteBottomSheetVisibility(true)
                                actions.setSelectedTaskId(taskUi.id)
                            },
                            modifier = Modifier
                                .clickable {
                                    actions.onTaskClick(taskUi.id)
                                }
                        )
                    }
                }
            }

            if (state.isDeleteBottomSheetVisible) {
                AlertBottomSheet(
                    title = stringResource(R.string.delete_task),
                    imgRes = R.drawable.sure_robot,
                    onRedBtnClick = {
                        actions.deleteTask(state.selectedTaskId)
                    },
                    onBlueBtnClick = { actions.setDeleteBottomSheetVisibility(false) }
                )
            }
        }

        FabButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 8.dp),
            icon = painterResource(R.drawable.add_task),
            onClick = { actions.onAddTaskClick() }
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
                modifier = Modifier.align(Alignment.BottomCenter),
                onSuccess = { message ->
                    actions.showSnackBarMessage(context.getString(R.string.task_edited_successfully), hasError = false)
                },
                onError = { error ->
                    actions.showSnackBarMessage(error, hasError = true)
                }
            )
        }

        AnimatedVisibility(state.isSnackBarVisible) {
            TudeeSnackBar(
                message = if (state.error == null && !state.snackBarHasError) stringResource(R.string.deleted_task_successfully) else stringResource(R.string.some_error_happened),
                iconRes = if (state.error == null && !state.snackBarHasError) R.drawable.ic_success else R.drawable.ic_error,
                iconTintColor = if (state.error == null && !state.snackBarHasError) Theme.color.greenAccent else Theme.color.error,
                iconBackgroundColor = if (state.error == null && !state.snackBarHasError) Theme.color.greenVariant else Theme.color.errorVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
        }
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