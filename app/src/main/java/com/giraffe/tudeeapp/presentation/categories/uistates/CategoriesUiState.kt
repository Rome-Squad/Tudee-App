package com.giraffe.tudeeapp.presentation.categories.uistates

import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.model.category.Category
import com.giraffe.tudeeapp.domain.util.DomainError

data class CategoriesUiState(
    val categories: List<CategoryUi> = emptyList(),
    val isLoading: Boolean = true,
    val error: DomainError? = null
)

data class CategoryUi(
    val id: Long = 1,
    val name: String = "",
    val countTasks: Int = 0,
    val imageUri: String,
)

fun Category.toUiState(countTasks: Int): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        countTasks = countTasks,
        imageUri = imageUri!!,
    )
}
