package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class HomeUiState(
    val allTasks: List<TaskUi> = emptyList(),
    val todoTasks: List<TaskUi> = emptyList(),
    val inProgressTasks: List<TaskUi> = emptyList(),
    val doneTasks: List<TaskUi> = emptyList(),
    val isTaskDetailsVisible: Boolean = false,
    val isTaskEditorVisible: Boolean = false,
    val currentTaskId: Long? = null,
    val isLoading: Boolean = true,
)