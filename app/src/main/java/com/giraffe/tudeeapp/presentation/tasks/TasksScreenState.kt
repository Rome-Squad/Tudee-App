package com.giraffe.tudeeapp.presentation.tasks

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TasksScreenState(
    val selectedDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val selectedTab: TaskStatus = TaskStatus.DONE,
    val selectedTaskId: Long = 0L,
    val currentTaskId: Long? = null,

    val tasks: Map<TaskStatus, List<TaskUi>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList()
    ),

    val isTaskDetailsBottomSheetVisible: Boolean = false,
    val isTaskEditorBottomSheetVisible: Boolean = false,
    val isDeleteTaskBottomSheetVisible: Boolean = false,
)