package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import com.giraffe.tudeeapp.design_system.component.StatusTab
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

data class TasksByCategoryScreenState(
    val selectedCategory: CategoryUi = CategoryUi(),
    val selectedTab: StatusTab = StatusTab.IN_PROGRESS,
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val error: DomainError? = null
)

