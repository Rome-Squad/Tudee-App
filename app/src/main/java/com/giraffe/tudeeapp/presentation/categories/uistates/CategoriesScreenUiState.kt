package com.giraffe.tudeeapp.presentation.categories.uistates

import androidx.core.net.toUri
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.util.DomainError
import kotlinx.serialization.Serializable

data class CategoriesScreenUiState(
    val categories: List<CategoryUi> = emptyList(),
    val isLoading: Boolean = true,
    val error: DomainError? = null,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
    val showSuccessSnackBar: Boolean = false
)

@Serializable
data class CategoryUi(
    val id: Long = 0L,
    val name: String = "no name",
    val taskCount: Int = 0,
    val imageUri: String = ("android.resource://com.giraffe.tudeeapp/${R.drawable.body_part_muscle}".toUri()).toString(),
    val isEditable: Boolean = false,
)