package com.giraffe.tudeeapp.presentation.utils

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi

fun List<Task>.toTaskUiList(categories: List<Category>): List<TaskUi> {
    val categoryMap = categories.associateBy { it.id }
    return this.mapNotNull { task ->
        categoryMap[task.categoryId]?.let { task.toTaskUi(it) }
    }
}