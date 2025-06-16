package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.design_system.component.StatusTab
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksByCategoryViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService
) : ViewModel(), TasksByCategoryScreenActions {
    private val _state = MutableStateFlow(TasksByCategoryScreenState())
    val state = _state.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByCategory(_state.value.selectedCategory.id).collect { result ->
                result.onSuccess { tasks ->
                    _state.update {
                        it.copy(
                            todoTasks = tasks.filter { it.status.name == StatusTab.TO_DO.name },
                            inProgressTasks = tasks.filter { it.status.name == StatusTab.IN_PROGRESS.name },
                            doneTasks = tasks.filter { it.status.name == StatusTab.DONE.name },
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(error = error) }
                }
            }

        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isBottomSheetVisible = isVisible) }
        }
    }

    override fun selectTab(tab: StatusTab) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }
}