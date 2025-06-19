package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

interface TasksScreenActions {
    fun setPickedDate(date: LocalDateTime)

    fun selectTab(status: TaskStatus)

    fun setAddBottomSheetVisibility(isVisible: Boolean)

    fun setDeleteBottomSheetVisibility(isVisible: Boolean)

    fun setSelectedTaskId(id: Long)

    fun showSnackBarMessage(message: String, hasError: Boolean = false)

    fun deleteTask(id: Long)
}