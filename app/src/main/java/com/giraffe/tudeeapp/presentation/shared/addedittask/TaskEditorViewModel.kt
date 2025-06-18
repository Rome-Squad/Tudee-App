package com.giraffe.tudeeapp.presentation.shared.addedittask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskEditorViewModel(
    private val taskId: Long? = null,
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskEditorBottomSheetUiState())
    val taskState: StateFlow<TaskEditorBottomSheetUiState> = _taskState

    init {
        loadCategories()
        taskId?.let { loadTask(it) }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            categoriesService.getAllCategories().onSuccess { flow ->
                flow.collect { categories ->
                    updateState {
                        copy(
                            categories = categories,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                updateState {
                    copy(
                        isLoading = false,
                        errorMessage = error.toString()
                    )
                }
            }
        }
    }

    private fun loadTask(id: Long) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            tasksService.getTaskById(id).onSuccess { task ->
                updateState {
                    copy(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate,
                        taskPriority = task.taskPriority,
                        taskStatus = task.status,
                        categoryId = task.categoryId,
                        isLoading = false
                    )
                }
            }.onError { error ->
                updateState { copy(isLoading = false, errorMessage = error.toString()) }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        updateState { copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        updateState { copy(description = newDescription) }
    }

    fun onDueDateChange(newDueDateMillis: Long?) {
        val newDueDate = newDueDateMillis?.let {
            Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
        }

        updateState {
            copy(
                dueDateMillis = newDueDateMillis,
                dueDate = newDueDate ?: this.dueDate
            )
        }
    }



    fun onPriorityChange(priority: TaskPriority) {
        updateState { copy(taskPriority = priority) }
    }

    fun onCategoryChange(categoryId: Long) {
        updateState { copy(categoryId = categoryId) }
    }

    fun onCancel() {
        clearCurrentTask()
    }

    fun saveTask() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

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

            result.onSuccess {
                clearCurrentTask()
                updateState { copy(isSuccessSave = true, isLoading = false) }
            }.onError { error ->
                clearCurrentTask()
                updateState { copy(isLoading = false, errorMessage = error.toString()) }
            }
        }
    }


    private fun updateState(update: TaskEditorBottomSheetUiState.() -> TaskEditorBottomSheetUiState) {
        val updated = _taskState.value.update()
        val validated = updated.copy(isValidInput = validateInputs(updated))
        _taskState.value = validated
    }

    private fun validateInputs(state: TaskEditorBottomSheetUiState): Boolean {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val isValidDueDate = state.dueDate.date >= now.date
        return state.title.isNotBlank() &&
                state.description.isNotBlank() &&
                state.categoryId != null && isValidDueDate
    }

    private fun clearCurrentTask() {
        updateState {
            copy(
                id = null,
                title = "",
                description = "",
                dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                dueDateMillis = null,
                taskPriority = TaskPriority.MEDIUM,
                taskStatus = TaskStatus.TODO,
                categoryId = null,
                categories = emptyList(),
                isLoading = false,
                isSuccessSave = false,
                errorMessage = null,
                isValidInput = false
            )
        }
    }
}
