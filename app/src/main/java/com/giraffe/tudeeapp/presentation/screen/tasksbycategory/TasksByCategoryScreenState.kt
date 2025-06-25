package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

data class TasksByCategoryScreenState(
    val selectedCategory: Category? = null,
    val selectedTab: TaskStatus = TaskStatus.TODO,
    val tasks: Map<TaskStatus, List<Task>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList(),
    ),
    val isAlertBottomSheetVisible: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
)

