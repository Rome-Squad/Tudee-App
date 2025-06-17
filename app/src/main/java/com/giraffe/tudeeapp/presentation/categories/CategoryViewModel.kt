package com.giraffe.tudeeapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.categories.uiEvent.CategoriesUiEvent
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoriesScreenUiState
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi
import com.giraffe.tudeeapp.presentation.utils.toCategory
import com.giraffe.tudeeapp.presentation.utils.toUiState
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
) : ViewModel(), CategoriesScreenAction {

    private var _categoriesUiState = MutableStateFlow(CategoriesScreenUiState())
    val categoriesUiState: StateFlow<CategoriesScreenUiState> = _categoriesUiState.asStateFlow()

    private val _events = Channel<CategoriesUiEvent>()
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
                                categories = categories.map { category -> category.toUiState() },
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

    override fun selectCategory(category: CategoryUi) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.send(CategoriesUiEvent.NavigateToTasksByCategoryScreen(category))
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        _categoriesUiState.update { it.copy(isBottomSheetVisible = isVisible) }
    }

    override fun addCategory(category: CategoryUi) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.createCategory(category.toCategory())
                .onSuccess {
                    _categoriesUiState.update {
                        it.copy(
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false
                        )
                    }
                    delay(3000)
                    _categoriesUiState.update { it.copy(showSuccessSnackBar = false) }
                }.onError { error ->
                    _categoriesUiState.update { it.copy(error = error) }
                }
        }
    }
}