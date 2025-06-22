package com.giraffe.tudeeapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoriesService: CategoriesService,
) : ViewModel(), CategoriesScreenActions {

    private var _categoriesUiState = MutableStateFlow(CategoriesScreenState())
    val categoriesUiState: StateFlow<CategoriesScreenState> = _categoriesUiState.asStateFlow()

    private val _events = Channel<CategoriesScreenEvents>()
    val events = _events.receiveAsFlow()


    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoriesUiState.update { it.copy(isLoading = true, error = null) }
            categoriesService.getAllCategories()
                .onSuccess { flow ->
                    flow.collect { categories ->
                        _categoriesUiState.update { currentState ->
                            currentState.copy(
                                categories = categories,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }.onError { error ->
                    _categoriesUiState.update { it.copy(isLoading = false, error = error) }
                }

        }
    }

    override fun selectCategory(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.send(CategoriesScreenEvents.NavigateToTasksByCategoryScreen(categoryId))
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        _categoriesUiState.update {
            it.copy(
                isBottomSheetVisible = isVisible,
                error = if (isVisible) it.error else null,
            )
        }
    }

    override fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.createCategory(category)
                .onSuccess {
                    _categoriesUiState.update {
                        it.copy(
                            isSnackBarVisible = true,
                            isBottomSheetVisible = false
                        )
                    }
                    delay(3000)
                    _categoriesUiState.update { it.copy(isSnackBarVisible = false) }
                }.onError { error ->
                    _categoriesUiState.update {
                        it.copy(
                            isSnackBarVisible = true,
                            error = error
                        )
                    }
                    delay(3000)
                    _categoriesUiState.update {
                        it.copy(
                            isSnackBarVisible = false,
                            error = null
                        )
                    }
                }
        }
    }
}