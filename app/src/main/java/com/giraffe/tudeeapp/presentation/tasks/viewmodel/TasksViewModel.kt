package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime


class TasksViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService
) : ViewModel() {

    private val _state = MutableStateFlow(TasksScreenState())
    val state = _state.asStateFlow()

    init {
        getTasks(_state.value.pickedDate)
    }

    private fun getTasks(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = tasksService.getTasksByDate(date)
            result.onSuccess { flow ->
                flow.collect { tasks ->
                    _state.update {
                        it.copy(
                            todoTasks = tasks.filter { it.status == TaskStatus.TODO },
                            inProgressTasks = tasks.filter { it.status == TaskStatus.IN_PROGRESS },
                            doneTasks = tasks.filter { it.status == TaskStatus.DONE },
                        )
                    }
                }
            }.onError { error ->
                _state.update { it.copy(error = error) }
            }
        }
    }

    fun setPickedDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(pickedDate = date) }
            getTasks(date)
        }
    }

    fun setBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isBottomSheetVisible = isVisible) }
        }
    }

    fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    fun getCategoryById(categoryId: Long): Category {
        var category: Category? = null
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.getCategoryById(categoryId)
                .onSuccess { category = it }
                .onError { }
        }
        return category ?: Category(id = 0, name = "Unknown", imageUri = "", isEditable = false
        , taskCount = 0)
    }

    fun confirmDelete(task: TaskUi) {
        _state.update { it.copy(taskToDelete = task) }
    }

    fun cancelDelete() {
        _state.update { it.copy(taskToDelete = null) }
    }

    fun deleteConfirmed() {
        val task = _state.value.taskToDelete ?: return
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.deleteTask(task.id)
            getTasks(_state.value.pickedDate)
            _state.update { it.copy(taskToDelete = null) }
        }
    }


}

