package com.giraffe.tudeeapp.presentation.screen.categories

import android.net.Uri
import com.giraffe.tudeeapp.domain.entity.Category

interface CategoriesScreenInteractionListener {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: Category)
    fun onCategoryTitleChanged(title: String)
    fun onCategoryImageChanged(uri: Uri)
}