package com.giraffe.tudeeapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TaskUi
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TasksScreenState
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TasksViewModel
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.toTaskUi
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun TaskScreen(
    viewModel: TasksViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    TaskScreenContent(state, viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenContent(
    state: TasksScreenState = TasksScreenState(),
    actions: TasksViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            HeaderContent("Tasks")

            DatePicker(actions::setPickedDate)

            TabsBar(
                onTabSelected = actions::selectTab,
                tasks = state.tasks.mapValues { (_, value) -> value.size })

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val selectedTasks = state.tasks[state.selectedTab]
                val selectedTasksSize = selectedTasks?.size ?: 0
                if (selectedTasksSize == 0) {
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
                    return@LazyColumn
                }

                items(selectedTasksSize) { index ->
                    val category =
                        actions.getCategoryById(selectedTasks?.get(index)?.categoryId ?: 0L)
                    val taskUi = selectedTasks?.get(index)?.toTaskUi(category)!!
                    SwipableTask(
                        taskUi = taskUi,
                        action = actions::setBottomSheetVisibility,
                        onDeleteClick = { actions.deleteTask(it.id) }
                    )
                }
            }

            if (state.isBottomSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { actions.setBottomSheetVisibility(false) },
                    modifier = Modifier.fillMaxHeight(0.95f)
                ) {
                    // Replace with your  add task content
                }
            }
        }

        FabButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 8.dp), // adjust bottom padding as needed
            icon = painterResource(R.drawable.add_task),
            onClick = { actions.setBottomSheetVisibility(true) }
        )

        AnimatedVisibility(state.isSnackBarVisible) {
            TudeeSnackBar(
                message = if (state.error == null) state.snackBarMsg else "Some error happened",
                iconRes = if (state.error == null) R.drawable.ic_success else R.drawable.ic_error,
                iconTintColor = if (state.error == null) Theme.color.greenAccent else Theme.color.error,
                iconBackgroundColor = if (state.error == null) Theme.color.greenVariant else Theme.color.errorVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun dummyTask(): TaskUi {
    return TaskUi(
        id = 1,
        title = "Sample Task",
        description = "This is a sample task description.",
        priorityType = TaskPriority.MEDIUM,
        status = TaskStatus.TODO,
        dueDate = LocalDateTime(2023, 10, 1, 12, 0),
        categoryName = "Work",
        icon = R.drawable.chef,
        color = Theme.color.pinkAccent,
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}


