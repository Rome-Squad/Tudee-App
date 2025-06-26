package com.giraffe.tudeeapp.presentation.screen.categories

sealed class CategoriesScreenEffect {
    data class NavigateToTasksByCategoryScreen(val categoryId: Long) : CategoriesScreenEffect()
    data class Error(val error: Throwable) : CategoriesScreenEffect()
    object CategoryAdded : CategoriesScreenEffect()
}