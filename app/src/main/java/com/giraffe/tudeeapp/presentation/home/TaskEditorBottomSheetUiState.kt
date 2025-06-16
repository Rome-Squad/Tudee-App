package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.domain.model.category.Category
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
    val isLoadingCategories: Boolean = false,
    val errorMessageCategories: String? = null,

    val isLoadingTask: Boolean = false,
    val errorMessageTask: String? = null,


    val isLoadingSave: Boolean = false,
    val isSuccessSave: Boolean = false,
    val errorMessageSave: String? = null,

    val isValidInput: Boolean = false

)

