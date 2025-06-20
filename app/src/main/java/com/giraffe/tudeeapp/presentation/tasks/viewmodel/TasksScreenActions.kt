package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

interface TasksScreenActions {
    fun setPickedDate(date: LocalDateTime)

    fun selectTab(status: TaskStatus)

    fun setDeleteBottomSheetVisibility(isVisible: Boolean)

    fun setSelectedTaskId(id: Long)

    fun showSnackBarMessage(message: String, hasError: Boolean = false)

    fun deleteTask(id: Long)


    fun onAddTaskClick()
    fun onTaskClick(taskId: Long)
    fun onEditTaskClick(taskId: Long?)
    fun dismissTaskDetails()
    fun dismissTaskEditor()
}