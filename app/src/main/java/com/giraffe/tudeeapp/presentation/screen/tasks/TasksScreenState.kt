package com.giraffe.tudeeapp.presentation.screen.tasks

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.presentation.utils.getCurrentLocalDate
import kotlinx.datetime.LocalDate

data class TasksScreenState(
    val selectedDate: LocalDate = getCurrentLocalDate(),
    val taskEditorDate: LocalDate = getCurrentLocalDate(),

    val selectedTab: TaskStatus = TaskStatus.DONE,
    val selectedTaskId: Long = 0L,
    val currentTaskId: Long? = null,

    val tasks: Map<TaskStatus, List<Task>> = mapOf(
        TaskStatus.TODO to emptyList(),
        TaskStatus.IN_PROGRESS to emptyList(),
        TaskStatus.DONE to emptyList()
    ),

    val isTaskDetailsBottomSheetVisible: Boolean = false,
    val isTaskEditorBottomSheetVisible: Boolean = false,
    val isDeleteTaskBottomSheetVisible: Boolean = false,
)