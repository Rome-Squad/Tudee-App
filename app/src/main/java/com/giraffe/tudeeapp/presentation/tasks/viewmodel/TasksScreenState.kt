package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TasksScreenState(
    val pickedDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val tasks: Map<TaskStatus, List<Task>> = mapOf(),
    val isAddBottomSheetVisible: Boolean = false,
    val isDeleteBottomSheetVisible: Boolean = false,
    val error: DomainError? = null,
    val isSnackBarVisible: Boolean = false,
    val snackBarMsg: String = "",
)

