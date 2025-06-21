package com.giraffe.tudeeapp.presentation.taskdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val taskId: Long,
    val taskService: TasksService,
    val categoryService: CategoriesService
) : ViewModel() {
    var taskDetailsState by mutableStateOf<TaskDetailsState>(TaskDetailsState())
        private set

    init {
        getTaskById(taskId)
    }

    fun getTaskById(taskId: Long) = viewModelScope.launch {
        taskDetailsState = taskDetailsState.copy(
            isLoading = true
        )
        taskService.getTaskById(taskId)
            .onSuccess { task ->
                categoryService.getCategoryById(task.categoryId)
                    .onSuccess { category ->
                        taskDetailsState = taskDetailsState.copy(
                            task = task.toTaskUi(category),
                            isLoading = false
                        )
                    }
                    .onError {
                        taskDetailsState = taskDetailsState.copy(
                            error = it
                        )
                    }
            }
            .onError {
                taskDetailsState = taskDetailsState.copy(
                    error = it,
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
                        error = it,
                        isLoading = false
                    )
                }
        }
    }
}