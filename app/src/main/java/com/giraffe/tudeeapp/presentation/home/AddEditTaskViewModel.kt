package com.giraffe.tudeeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AddEditTaskViewModel(
    private val tasksService: TasksService,
    private val taskId: Long? = null
) : ViewModel() {

    private val _taskState = MutableStateFlow(AddEditTaskUiState())
    val taskState: StateFlow<AddEditTaskUiState> = _taskState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        if (taskId != null) {
            loadTask(taskId)
        }
    }

   private fun loadTask(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            tasksService.getTaskById(id)
                .onSuccess { task ->
                    _taskState.value = AddEditTaskUiState(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate,
                        taskPriority = task.taskPriority,
                        taskStatus = task.status,
                        categoryId = task.categoryId
                    )
                }
                .onError {errorMassage ->
                    _error.value = errorMassage.toString()
                }

            _isLoading.value = false
        }
    }

    fun onTitleChange(newTitle: String) {
        _taskState.value = _taskState.value.copy(title = newTitle)
    }

    fun onDescriptionChange(newDescription: String) {
        _taskState.value = _taskState.value.copy(description = newDescription)
    }

    fun onDueDateChange(newDueDate: LocalDateTime) {
        _taskState.value = _taskState.value.copy(dueDate = newDueDate)
    }

    fun onPriorityChange(priority: TaskPriority) {
        _taskState.value = _taskState.value.copy(taskPriority = priority)
    }

    fun onStatusChange(status: TaskStatus) {
        _taskState.value = _taskState.value.copy(taskStatus = status)
    }

    fun onCategoryChange(categoryId: Long) {
        _taskState.value = _taskState.value.copy(categoryId = categoryId)
    }

    fun saveTask() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val currentState = _taskState.value
            val task = Task(
                id = currentState.id ?: 0L,
                title = currentState.title,
                description = currentState.description,
                taskPriority = currentState.taskPriority,
                status = currentState.taskStatus,
                categoryId = currentState.categoryId ?: 0L,
                dueDate = currentState.dueDate,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            val result = if (taskId == null) {
                tasksService.createTask(task)
            } else {
                tasksService.updateTask(task)
            }

            result
                .onSuccess {
                    _isSuccess.value = true
                }
                .onError { errorMassage ->
                    _error.value = errorMassage.toString()
                }

            _isLoading.value = false
        }
    }
    fun resetSuccess() {
        _isSuccess.value = false
    }

}