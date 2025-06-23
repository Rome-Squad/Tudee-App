package com.giraffe.tudeeapp.presentation.taskeditor

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDate

interface TaskEditorActions {
    fun cancel()

    fun onChangeTaskTitleValue(title: String)
    fun onChangeTaskDescriptionValue(description: String)
    fun onChangeTaskDueDateValue(dueDate: LocalDate)
    fun onChangeTaskPriorityValue(priority: TaskPriority)
    fun onChangeTaskCategoryValue(categoryId: Long)
    fun onChangeTaskStatusValue(status: TaskStatus)
    fun addTask(task: Task)
    fun editTask(task: Task)

}