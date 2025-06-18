package com.giraffe.tudeeapp.presentation.categories.state

sealed class CategoriesUiEvent {
    data class NavigateToTasksByCategoryScreen(val categoryId: Long) : CategoriesUiEvent()
}