package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import com.giraffe.tudeeapp.design_system.component.StatusTab

interface TasksByCategoryScreenActions {
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: StatusTab)
}