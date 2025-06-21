package com.giraffe.tudeeapp.presentation.tasks

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TasksScreenState(
    val pickedDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val selectedTab: TaskStatus = TaskStatus.DONE,
    val tasks: Map<TaskStatus, List<TaskUi>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList()
    ),
    val selectedTaskId: Long = 0L,
    val isDeleteBottomSheetVisible: Boolean = false,
    val error: DomainError? = null,
    val isSnackBarVisible: Boolean = false,
    val snackBarMsg: String = "",
    val snackBarHasError: Boolean = false,


    val isTaskDetailsVisible: Boolean = false,
    val isTaskEditorVisible: Boolean = false,
    val currentTaskId: Long? = null,
)