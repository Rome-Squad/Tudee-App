package com.giraffe.tudeeapp.presentation.screen.taskeditor

import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.presentation.utils.emptyTask

data class TaskEditorState(
    val task: Task = emptyTask(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccessAdded: Boolean = false,
    val isSuccessEdited: Boolean = false,
    val isValidTask: Boolean = false,
)

