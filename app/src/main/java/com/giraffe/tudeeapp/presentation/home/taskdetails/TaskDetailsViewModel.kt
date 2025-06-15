package com.giraffe.tudeeapp.presentation.home.taskdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.ValidationError
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    val taskService: TasksService,
    val categoryService: CategoriesService
): ViewModel() {
    var taskDetailsState by mutableStateOf<TaskDetailsState>(TaskDetailsState())
        private set
    var showEditTaskSheet by mutableStateOf(false)
        private set

    fun getTaskById(taskId: Long) = viewModelScope.launch {
        taskDetailsState = taskDetailsState.copy(
            isLoading = true
        )
        taskService.getTaskById(taskId)
            .onSuccess { task ->
                categoryService.getCategoryById(task.categoryId)
                    .onSuccess { category ->
                        taskDetailsState = taskDetailsState.copy(
                            taskCategory = category
                        )
                    }
                    .onError {
                        taskDetailsState = taskDetailsState.copy(
                            error = errorToMessage(it)
                        )
                    }
                taskDetailsState = taskDetailsState.copy(
                    task = task,
                    isLoading = false
                )
            }
            .onError {
                taskDetailsState = taskDetailsState.copy(
                    error = errorToMessage(it),
                    isLoading = false
                )
            }
    }

    fun changeTaskStatus(newStatus: TaskStatus) = viewModelScope.launch {
        taskDetailsState = taskDetailsState.copy(
            isLoading = true
        )
        if (taskDetailsState.task != null) {
            taskService.changeStatus(taskDetailsState.task!!.id, newStatus)
                .onSuccess {
                    taskDetailsState = taskDetailsState.copy(
                        task = taskDetailsState.task!!.copy(
                            status = newStatus
                        ),
                        isLoading = false
                    )
                }
                .onError {
                    taskDetailsState = taskDetailsState.copy(
                        error = errorToMessage(it),
                        isLoading = false
                    )
                }
        }
    }

    fun showEditTaskSheet() {
        showEditTaskSheet = true
    }

    fun hideEditTaskSheet() {
        showEditTaskSheet = false
    }

    private fun errorToMessage(error: DomainError): String  = when (error) {
        is NotFoundError -> "Task Not Found!"
        is ValidationError -> "Sorry, Something Went Wrong"
        else -> "Sorry, An Unknown Error"
    }
}