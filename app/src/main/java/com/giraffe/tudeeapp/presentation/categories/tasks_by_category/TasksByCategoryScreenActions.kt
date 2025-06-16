package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

interface TasksByCategoryScreenActions {
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: TaskStatus)
    fun editCategory(category: CategoryUi)
}