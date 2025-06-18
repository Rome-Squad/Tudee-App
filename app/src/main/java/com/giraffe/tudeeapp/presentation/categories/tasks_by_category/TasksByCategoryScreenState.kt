package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import androidx.core.net.toUri
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError

data class TasksByCategoryScreenState(
    val selectedCategory: Category? = null,
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val tasks: Map<TaskStatus, List<TaskUiModel>> = mapOf(
        TaskStatus.TODO to List(3) { TaskUiModel(categoryIcon = ("android.resource://com.giraffe.tudeeapp/${R.drawable.airplane_01}".toUri()).toString()) },
        TaskStatus.IN_PROGRESS to List(5) { TaskUiModel() },
        TaskStatus.DONE to List(10) { TaskUiModel(categoryIcon = ("android.resource://com.giraffe.tudeeapp/${R.drawable.developer}".toUri()).toString()) },
    ),
    val isBottomSheetVisible: Boolean = false,
    val error: DomainError? = null,
    val showSuccessSnackBar: Boolean = false,
    val snackBarMsg: String = "",
)


data class TaskUiModel(
    val id: Long = 0L,
    val title: String = "title",
    val description: String = "description",
    val taskPriority: TaskPriority = TaskPriority.HIGH,
    val status: TaskStatus = TaskStatus.DONE,
    val categoryIcon: String = ("android.resource://com.giraffe.tudeeapp/${R.drawable.bug_01}".toUri()).toString(),
    val dueDate: String = "06 - 01 - 2025",
    val createdAt: String = "09 - 11 - 2025",
    val updatedAt: String = "04 - 07 - 2025"
)

