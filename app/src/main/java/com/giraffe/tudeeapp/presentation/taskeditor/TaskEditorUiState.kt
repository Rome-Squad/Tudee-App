package com.giraffe.tudeeapp.presentation.taskeditor

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.presentation.utils.emptyTask

data class TaskEditorUiState(
    val task: Task = emptyTask(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccessAdded: Boolean = false,
    val isSuccessEdited: Boolean = false,
    val isValidTask: Boolean = false,
)

