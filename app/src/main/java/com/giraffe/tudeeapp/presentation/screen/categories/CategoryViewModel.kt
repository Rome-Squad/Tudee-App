package com.giraffe.tudeeapp.presentation.screen.categories

import android.net.Uri
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel

class CategoryViewModel(
    private val categoriesService: CategoriesService,
) : BaseViewModel<CategoriesScreenState, CategoriesScreenEffect>(CategoriesScreenState()),
    CategoriesScreenInteractionListener {

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        safeCollect(
            onError = ::onGetAllCategoriesError,
            onEmitNewValue = ::onGetAllCategoriesNewValue
        ) {
            categoriesService.getAllCategories()
        }

    }

    private fun onGetAllCategoriesNewValue(categories: List<Category>) {
        updateState { currentState ->
            currentState.copy(
                categories = categories.map { category -> category },
                isLoading = false,
            )
        }
    }

    private fun onGetAllCategoriesError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(CategoriesScreenEffect.Error(error))
    }

    override fun selectCategory(categoryId: Long) {
        sendEffect(CategoriesScreenEffect.NavigateToTasksByCategoryScreen(categoryId))
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        updateState { it.copy(isBottomSheetVisible = isVisible) }
    }

    override fun addCategory(category: Category) {
        safeExecute(
            onError = ::onAddCategoryError,
            onSuccess = { onAddCategorySuccess() },
        ) {
            categoriesService.createCategory(category)
        }
    }

    override fun onCategoryTitleChanged(title: String) =
        updateState { it.copy(selectedCategoryTitle = title) }

    override fun onCategoryImageChanged(uri: Uri) =
        updateState { it.copy(selectedCategoryImageUri = uri) }

    private fun onAddCategorySuccess() {
        updateState {
            it.copy(
                selectedCategoryTitle = null,
                selectedCategoryImageUri = null,
                isBottomSheetVisible = false
            )
        }
        sendEffect(CategoriesScreenEffect.CategoryAdded)
    }

    private fun onAddCategoryError(error: Throwable) {
        updateState { it.copy(isBottomSheetVisible = false) }
        sendEffect(CategoriesScreenEffect.Error(error))
    }
}