package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class HomeUiState(
    val tasks: Map<TaskStatus, List<TaskUi>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList()
    ),
    val isTaskDetailsVisible: Boolean = false,
    val isTaskEditorVisible: Boolean = false,
    val currentTaskId: Long? = null,
    val isLoading: Boolean = true,
)