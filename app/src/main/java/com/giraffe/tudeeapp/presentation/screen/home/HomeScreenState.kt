package com.giraffe.tudeeapp.presentation.screen.home

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

data class HomeScreenState(
    val tasks: Map<TaskStatus, List<Task>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList()
    ),
    val isDarkTheme: Boolean = false,
    val isTaskDetailsVisible: Boolean = false,
    val isTaskEditorVisible: Boolean = false,
    val currentTaskId: Long? = null,
    val isLoading: Boolean = true,
)