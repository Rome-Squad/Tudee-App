package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.button_type.TudeeFabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Theme.color.surface),
                title = {
                    Text("Tasks")
                }
            )
        },
        bottomBar = {
            NavBar(navController = rememberNavController())
        },
        floatingActionButton = {
            TudeeFabButton(
                isLoading = false,
                isDisable = false,
                icon = painterResource(R.drawable.add_task),
                onClick = { }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Add the date picker here
            TabsBar()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                items(10) { index ->
                    SwipableTask(
                        task = Task(
                            id = 1L,
                            title = "Sample Task",
                            description = "This is a sample task description.",
                            taskPriority = TaskPriority.HIGH,
                            status = TaskStatus.DONE,
                            categoryId = 1L,
                            dueDate = LocalDateTime(2025, 3, 12, 0, 0),
                            createdAt = LocalDateTime(2025, 3, 1, 0, 0),
                            updatedAt = LocalDateTime(2025, 3, 2, 0, 0)
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}


