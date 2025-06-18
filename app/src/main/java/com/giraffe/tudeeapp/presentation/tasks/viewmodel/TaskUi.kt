package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.utils.getCategoryIcon
import com.giraffe.tudeeapp.presentation.utils.getColorForCategoryIcon
import kotlinx.datetime.LocalDateTime


data class TaskUi(
    val id: Long,
    val title: String,
    val description: String,
    val priorityType: TaskPriority,
    val status: TaskStatus,
    val categoryName: String,
    val dueDate: LocalDateTime,
    val icon: Int,
    val color: Color
)

@Composable
fun Task.toTaskUi(category: Category): TaskUi {
    return TaskUi(
        id = id,
        title = title,
        description = description,
        priorityType = taskPriority,
        status = status,
        categoryName = category.name,
        dueDate = dueDate,
        icon = getCategoryIcon(category.name),
        color = getColorForCategoryIcon(category.name)
    )
}