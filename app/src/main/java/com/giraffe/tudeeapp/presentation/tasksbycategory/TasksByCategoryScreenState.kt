package com.giraffe.tudeeapp.presentation.tasksbycategory

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class TasksByCategoryScreenState(
    val selectedCategory: Category? = null,
    val categoryToDelete: Category? = null,
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val tasks: Map<TaskStatus, List<TaskUi>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList(),
    ),
    val isAlertBottomSheetVisible: Boolean = false,
    val isBottomSheetVisible: Boolean = false,

    val error: DomainError? = null,
    val snakeBarMsg: String? = null,
)

