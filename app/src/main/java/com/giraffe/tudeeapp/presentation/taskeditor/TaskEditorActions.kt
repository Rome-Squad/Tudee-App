package com.giraffe.tudeeapp.presentation.taskeditor

import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

interface TaskEditorActions {
    fun setTaskId(taskId: Long?)
    fun saveTask()
    fun cancel()

    fun onChangeTaskTitleValue(title: String)
    fun onChangeTaskDescriptionValue(description: String)
    fun onChangeTaskDueDateValue(dueDate: LocalDateTime)
    fun onChangeTaskPriorityValue(priority: TaskPriority)
    fun onChangeTaskCategoryValue(categoryId: Long)
    fun onChangeTaskStatusValue(status: TaskStatus)

}