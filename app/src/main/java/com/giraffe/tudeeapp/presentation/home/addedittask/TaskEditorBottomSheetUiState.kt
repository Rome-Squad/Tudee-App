package com.giraffe.tudeeapp.presentation.home.addedittask

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskEditorBottomSheetUiState(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val dueDateMillis: Long? = null,
    val taskPriority: TaskPriority = TaskPriority.MEDIUM,
    val taskStatus: TaskStatus = TaskStatus.TODO,
    val categoryId: Long? = null,

    val categories: List<Category> = emptyList(),

    val isLoading: Boolean = false,
    val isSuccessSave: Boolean = false,
    val errorMessage: String? = null,

    val isValidInput: Boolean = false
)

