package com.giraffe.tudeeapp.presentation.taskeditor

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class TaskEditorUiState(
    val taskUi: TaskUi = TaskUi(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccessAdded: Boolean = false,
    val isSuccessEdited: Boolean = false,
    val isValidTask: Boolean = false,
)

