package com.giraffe.tudeeapp.presentation.tasksbycategory

sealed class TasksByCategoryEvents {
    class CategoryDeleted : TasksByCategoryEvents()
    class CategoryEdited : TasksByCategoryEvents()
}