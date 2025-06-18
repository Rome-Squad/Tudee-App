package com.giraffe.tudeeapp.presentation.categories

sealed class CategoriesScreenEvents {
    data class NavigateToTasksByCategoryScreen(val categoryId: Long) : CategoriesScreenEvents()
}