package com.giraffe.tudeeapp.presentation.utils

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

fun CategoryUi.toCategory() = Category(
    id = id,
    name = name,
    imageUri = imageUri,
    isEditable = isEditable,
    taskCount = taskCount,
)

fun Category.toUiState(): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        taskCount = taskCount,
        imageUri = imageUri,
        isEditable = isEditable
    )
}