package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import com.giraffe.tudeeapp.domain.util.Result


class TasksViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService
) : ViewModel(), TasksScreenActions {

    private val _state = MutableStateFlow(TasksScreenState())
    val state = _state.asStateFlow()

    init {
        getTasks(_state.value.pickedDate)
    }

    private fun getTasks(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByDate(date)
                .onSuccess { flow ->
                    flow.collect { tasks ->
                        try {
                            val tasksUiList = tasks.map { task ->
                                val categoryResult =
                                    categoryService.getCategoryById(task.categoryId)
                                val category = if (categoryResult is Result.Success) {
                                    categoryResult.data
                                } else {
                                    null
                                }
                                task.toTaskUi(category ?: throw Exception())
                            }.groupBy { it.status }

                            _state.update { currentState ->
                                currentState.copy(
                                    tasks = tasksUiList,
                                    pickedDate = date,
                                    error = null
                                )
                            }
                        } catch (e: Exception) {
                            _state.update { it.copy(error = NotFoundError()) }
                        }
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    override fun setPickedDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(pickedDate = date) }
            getTasks(date)
        }
    }

    override fun setAddBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isAddBottomSheetVisible = isVisible) }
        }
    }

    override fun setDeleteBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isDeleteBottomSheetVisible = isVisible) }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun showSnackBarMessage(message: String, hasError: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    snackBarMsg = message,
                    isSnackBarVisible = true,
                    snackBarHasError = hasError
                )
            }
            delay(3000)
            _state.update {
                it.copy(
                    snackBarMsg = "",
                    isSnackBarVisible = false,
                    snackBarHasError = false
                )
            }
        }
    }

    override fun setSelectedTaskId(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTaskId = taskId) }
        }
    }

    override fun deleteTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.deleteTask(taskId)
                .onSuccess {
                    _state.update {
                        it.copy(
                            snackBarMsg = "Deleted task successfully.",
                            isSnackBarVisible = true,
                            isDeleteBottomSheetVisible = false,
                            tasks = it.tasks.mapValues { entry ->
                                entry.value.filter { task -> task.id != taskId }
                            }
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(isSnackBarVisible = false) }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            isSnackBarVisible = true,
                            isDeleteBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(isSnackBarVisible = false) }
                }
        }
    }
}

