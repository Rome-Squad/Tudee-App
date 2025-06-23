package com.giraffe.tudeeapp.presentation.taskeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class TaskEditorViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
) : ViewModel(), TaskEditorActions {
    var taskEditorUiState = MutableStateFlow(TaskEditorUiState())
        private set

    private val _events = Channel<TaskEditorEvent>()
    val events = _events.receiveAsFlow()


    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            taskEditorUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            categoriesService.getAllCategories()
                .onSuccess { flow ->
                    val categories = flow.first()
                    taskEditorUiState.update {
                        it.copy(
                            categories = categories,
                            isLoading = false
                        )
                    }

                }
                .onError { error ->
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.Error(error))
                }
        }
    }

    fun loadTask(taskId: Long) {
        if (taskId == taskEditorUiState.value.taskUi.id ) return
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(id = taskId),
                isLoading = true
            )
        }
        viewModelScope.launch {
            tasksService.getTaskById(taskId)
                .onSuccess { task ->
                    val category = getCategoryById(task.categoryId)
                    if (category != null) {
                        taskEditorUiState.update {
                            it.copy(
                                taskUi = task.toTaskUi(category),
                                isLoading = false,
                                isValidTask = isValidTask()
                            )
                        }
                    } else {
                        taskEditorUiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _events.send(TaskEditorEvent.Error(NotFoundError()))
                    }
                }
                .onError { error ->
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.Error(error))
                }
        }
    }

    private fun getCategoryById(id: Long): Category? {
        return taskEditorUiState.value.categories.find { category ->
            category.id == id
        }
    }


    override fun addTask(task: Task) {
        viewModelScope.launch {
            taskEditorUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            tasksService.createTask(task)
                .onSuccess {
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.TaskAddedSuccess)
                    _events.send(TaskEditorEvent.DismissTaskEditor)
                    clearTask()
                }
                .onError {
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.Error(it))
                    _events.send(TaskEditorEvent.DismissTaskEditor)
                    clearTask()
                }

        }
    }

    override fun editTask(task: Task) {
        viewModelScope.launch {
            taskEditorUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            tasksService.updateTask(task)
                .onSuccess {
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.TaskEditedSuccess)
                    _events.send(TaskEditorEvent.DismissTaskEditor)
                    clearTask()
                }
                .onError {
                    taskEditorUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TaskEditorEvent.Error(it))
                    _events.send(TaskEditorEvent.DismissTaskEditor)
                    clearTask()
                }
        }
    }

    override fun cancel() {
        viewModelScope.launch {
            clearTask()
            _events.send(TaskEditorEvent.DismissTaskEditor)
        }
    }

    override fun onChangeTaskTitleValue(title: String) {
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(title = title)
            )
        }
        validateTask()
    }

    override fun onChangeTaskDescriptionValue(description: String) {
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(description = description)
            )
        }
        validateTask()
    }

    override fun onChangeTaskDueDateValue(dueDate: LocalDateTime) {
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(dueDate = dueDate)
            )
        }
        validateTask()
    }

    override fun onChangeTaskPriorityValue(priority: TaskPriority) {
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(priorityType = priority)
            )
        }
        validateTask()
    }

    override fun onChangeTaskCategoryValue(categoryId: Long) {
        viewModelScope.launch {
            val category = getCategoryById(categoryId)
            if (category != null) {
                taskEditorUiState.update {
                    it.copy(
                        taskUi = it.taskUi.copy(category = category),
                        isLoading = false,
                        isValidTask = isValidTask()
                    )
                }
                validateTask()
            } else {
                taskEditorUiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _events.send(TaskEditorEvent.Error(NotFoundError()))
            }
        }
    }

    override fun onChangeTaskStatusValue(status: TaskStatus) {
        taskEditorUiState.update {
            it.copy(
                taskUi = it.taskUi.copy(status = status)
            )
        }
    }

    private fun isValidTask(): Boolean {
        return !(taskEditorUiState.value.taskUi.title.isBlank() ||
                taskEditorUiState.value.taskUi.description.isBlank() ||
                taskEditorUiState.value.taskUi.category.name.isBlank() ||
                taskEditorUiState.value.taskUi.category.imageUri.isBlank()
                )
    }

    private fun validateTask() {
        taskEditorUiState.update {
            it.copy(
                isValidTask = isValidTask()
            )
        }
    }

    private fun clearTask() {
        taskEditorUiState.update {
            it.copy(
                taskUi = TaskUi(),
                isLoading = false,
                isValidTask = false,
                isSuccessAdded = false,
                isSuccessEdited = false
            )
        }
    }
}
