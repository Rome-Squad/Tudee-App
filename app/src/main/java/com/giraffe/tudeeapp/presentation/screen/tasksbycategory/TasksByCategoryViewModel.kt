package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.CategoryDeleted
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.CategoryEdited
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.DeleteCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.EditCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.GetCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEvents.GetTasksError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksByCategoryViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : ViewModel(), TasksByCategoryScreenActions {
    private val _state = MutableStateFlow(TasksByCategoryScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<TasksByCategoryEvents>()
    val events = _event.receiveAsFlow()

    init {
        getCategoryById(CategoriesArgs(savedStateHandle = savedStateHandle).categoryId)
    }

    private fun getCategoryById(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.getCategoryById(categoryId)
                .onSuccess { category ->
                    _state.update { state ->
                        state.copy(
                            selectedCategory = category,
                        )
                    }
                    getTasks(category)
                }
                .onError { error ->
                    _event.send(GetCategoryError(error))
                }
        }
    }

    private fun getTasks(category: Category) {
        viewModelScope.launch {
            tasksService.getTasksByCategory(category.id)
                .onSuccess { tasksFlow ->
                    tasksFlow.collect { tasks ->
                        val tasksInMap = TaskStatus.entries.associateWith { status ->
                            tasks.filter { it.status == status }
                        }
                        _state.update { it.copy(tasks = tasksInMap) }

                    }
                }
                .onError { error ->
                    _event.send(GetTasksError(error))
                }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun setAlertBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isAlertBottomSheetVisible = isVisible) }
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isBottomSheetVisible = isVisible) }
        }
    }

    override fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.updateCategory(category = category)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isBottomSheetVisible = false,
                            selectedCategory = category,
                        )
                    }
                    _event.send(CategoryEdited())
                }.onError { error ->
                    _event.send(EditCategoryError(error))
                }
        }
    }

    override fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.deleteCategory(category.id)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isBottomSheetVisible = false,
                        )
                    }
                    _event.send(CategoryDeleted())
                }.onError { error ->
                    _event.send(DeleteCategoryError(error))
                }
        }
    }
}