package com.giraffe.tudeeapp.presentation.categories.uiEvent

import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

sealed class CategoriesUiEvent {
    data class NavigateToTasksByCategoryScreen(val category: CategoryUi) : CategoriesUiEvent()
}