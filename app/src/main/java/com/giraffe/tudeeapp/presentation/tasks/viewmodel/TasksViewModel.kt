package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.category.Category
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
        val currentDate = LocalDateTime(
            year = 2025,
            monthNumber = 6,
            dayOfMonth = 25,
            hour = 0,
            minute = 0,
        )
        getTasks(currentDate)
    }

    private fun getTasks(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByDate(date).collect { result ->
                result.onSuccess { tasks ->
                    _state.update {
                        it.copy(
                            todoTasks = tasks.filter { it.status.name == TaskStatus.TODO.name },
                            inProgressTasks = tasks.filter { it.status.name == TaskStatus.IN_PROGRESS.name },
                            doneTasks = tasks.filter { it.status.name == TaskStatus.DONE.name },
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(error = error) }
                }
            }
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
        return category ?: Category(id = 0, name = "Unknown", imageUri = "")
    }

}

