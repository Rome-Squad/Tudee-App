package com.giraffe.tudeeapp.presentation.tasksbycategory

import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

interface TasksByCategoryScreenInteractionListener {
    fun setAlertBottomSheetVisibility(isVisible: Boolean)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: TaskStatus)
    fun editCategory(category: Category)
    fun deleteCategory(category: Category)
}