package com.giraffe.tudeeapp.presentation.categories.uiEvent

sealed class CategoriesUiEvent {
    data class NavigateToTasksByCategoryScreen(val categoryId: Long) : CategoriesUiEvent()
}