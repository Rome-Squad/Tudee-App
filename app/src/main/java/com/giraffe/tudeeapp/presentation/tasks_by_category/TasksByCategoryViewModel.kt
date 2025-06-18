package com.giraffe.tudeeapp.presentation.tasks_by_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

    private val _events = Channel<TasksByCategoryEvents>()
    val events = _events.receiveAsFlow()

    init {
        getCategoryById(savedStateHandle.get<Long>(Screen.TasksByCategoryScreen.CATEGORY_ID) ?: 0L)
        getTasks()
    }

    private fun getCategoryById(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {            categoriesService.getCategoryById(categoryId)
                .onSuccess { category ->
                    _state.update { state ->
                        state.copy(
                            selectedCategory = category,
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {}
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isBottomSheetVisible = isVisible) }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.updateCategory(category = category)
                .onSuccess {
                    _state.update {
                        it.copy(
                            selectedCategory = category,
                            snackBarMsg = "Edited category successfully.",
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(showSuccessSnackBar = false) }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(showSuccessSnackBar = false) }
                }
        }
    }

    override fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.deleteCategory(category.id)
                .onSuccess {
                    _state.update {
                        it.copy(
                            snackBarMsg = "Deleted category successfully.",
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(showSuccessSnackBar = false) }
                    _events.send(TasksByCategoryEvents.CategoryDeleted())
                }.onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(showSuccessSnackBar = false) }
                }
        }
    }
}