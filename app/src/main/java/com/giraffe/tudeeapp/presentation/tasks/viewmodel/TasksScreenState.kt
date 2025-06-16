package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi
import kotlinx.datetime.LocalDateTime

data class TasksScreenState(
    val selectedCategory: CategoryUi = CategoryUi(),
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val error: DomainError? = null
)

