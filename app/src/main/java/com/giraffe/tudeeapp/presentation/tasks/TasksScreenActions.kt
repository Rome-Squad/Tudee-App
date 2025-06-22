package com.giraffe.tudeeapp.presentation.tasks

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

interface TasksScreenActions {
    fun setSelectedDate(date: LocalDateTime)
    fun setSelectedTab(status: TaskStatus)
    fun setSelectedTaskId(id: Long)

    fun onTaskClick(taskId: Long)
    fun onDeleteTaskClick()
    fun onAddTaskClick()
    fun onEditTaskClick(taskId: Long?)

    fun onDismissTaskDetailsBottomSheetRequest()
    fun onDismissTaskEditorBottomSheetRequest()
    fun onDismissDeleteTaskBottomSheetRequest()

    fun onConfirmDeleteTask(id: Long)
}