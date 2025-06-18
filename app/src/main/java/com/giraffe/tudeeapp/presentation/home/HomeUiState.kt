package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class HomeUiState(
    val allTasks: List<TaskUi> = emptyList(),
    val todoTasks: List<TaskUi> = emptyList(),
    val inProgressTasks: List<TaskUi> = emptyList(),
    val doneTasks: List<TaskUi> = emptyList(),
    val allTasksCount: Int = 0,
    val toDoTasksCount: Int = 0,
    val inProgressTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
    val isOpenTaskDetailsBottomSheet: Boolean = false,
    val isOpenAddEditTaskBottomSheet: Boolean = false,
    val currentTaskId: Long? = null,
    val isLoading: Boolean = true,
    val errorMessage: DomainError? = null
)