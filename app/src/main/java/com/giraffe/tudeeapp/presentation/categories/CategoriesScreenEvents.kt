package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.domain.util.DomainError

sealed class CategoriesScreenEvents {
    data class NavigateToTasksByCategoryScreen(val categoryId: Long) : CategoriesScreenEvents()
    data class Error(val error: DomainError) : CategoriesScreenEvents()
    object CategoryAdded : CategoriesScreenEvents()
}