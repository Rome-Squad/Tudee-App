package com.giraffe.tudeeapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TaskUi
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TasksScreenState
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.TasksViewModel
import com.giraffe.tudeeapp.presentation.tasks.viewmodel.toTaskUi
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    TaskScreenContent(state, viewModel)
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenContent(
    state: TasksScreenState = TasksScreenState(),
    actions: TasksViewModel
) {
    Scaffold(
        containerColor = Theme.color.surfaceHigh,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
                        style = Theme.textStyle.title.large,
                        color = Theme.color.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Theme.color.surfaceHigh)
                            .padding(vertical = 20.dp, horizontal = 16.dp)
                    )
                }
            )
        },
        bottomBar = {
            NavBar(navController = rememberNavController())
        },
        floatingActionButton = {
            FabButton(
                icon = painterResource(R.drawable.add_task),
                onClick = { actions::setBottomSheetVisibility }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DatePicker(actions::setPickedDate)

            TabsBar(onTabSelected = actions::selectTab)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier,
            ) {
                var selectedTasks = when (state.selectedTab) {
                    TaskStatus.TODO -> state.todoTasks
                    TaskStatus.IN_PROGRESS -> state.inProgressTasks
                    TaskStatus.DONE -> state.doneTasks
                }
                if (selectedTasks.isEmpty()) {
                    item {
                       NoTasksSection()
                    }
                    return@LazyColumn
                }
                items(selectedTasks.size) { index ->
                    val category = actions.getCategoryById(selectedTasks[index].categoryId)
                    val taskUi = selectedTasks[index].toTaskUi(category)
                    SwipableTask(
                        taskUi = taskUi,
                        action = actions::setBottomSheetVisibility,
                    )
                }
//                items(10) { index ->
//                    SwipableTask(
//                        dummyTask(),
//                        action = actions::setBottomSheetVisibility
//                    )
//                }

            }
        }
    }
}

@Composable
fun dummyTask(): TaskUi {
    return TaskUi(
        id = 1,
        title = "Sample Task",
        description = "This is a sample task description.",
        priorityType = PriorityType.MEDIUM,
        status = TaskStatus.TODO,
        dueDate = LocalDateTime(2023, 10, 1, 12, 0),
        categoryName = "Work",
        icon = R.drawable.chef,
        color = Theme.color.pinkAccent,
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}


