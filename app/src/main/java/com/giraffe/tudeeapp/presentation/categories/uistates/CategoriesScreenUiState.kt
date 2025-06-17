package com.giraffe.tudeeapp.presentation.categories.uistates

import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.utils.getCategoryIcon

data class CategoriesScreenUiState(
    val categories: List<CategoryUi> = emptyList(),
    val isLoading: Boolean = true,
    val error: DomainError? = null,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
    val showSuccessSnackBar: Boolean = false
)

data class CategoryUi(
    val id: Long = 1,
    val name: String = "",
    val taskCount: Int = 0,
    val imageUri: String? = null,
    val icon: Int = R.drawable.airplane_01,
)


fun Category.toUiState(countTasks: Int): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        taskCount = countTasks,
        imageUri = imageUri,
        icon = getCategoryIcon(name),
    )
}
