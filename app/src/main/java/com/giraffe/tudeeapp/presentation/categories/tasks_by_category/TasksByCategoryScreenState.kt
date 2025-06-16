package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

data class TasksByCategoryScreenState(
    val selectedCategory: CategoryUi = CategoryUi(),
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val tasks: Map<TaskStatus,List<Task>> = mapOf(),
    val isBottomSheetVisible: Boolean = false,
    val error: DomainError? = null
)

