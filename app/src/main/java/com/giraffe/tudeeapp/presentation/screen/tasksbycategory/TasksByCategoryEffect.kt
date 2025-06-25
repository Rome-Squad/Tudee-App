package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

sealed class TasksByCategoryEffect {
    class CategoryDeleted : TasksByCategoryEffect()
    class CategoryEdited : TasksByCategoryEffect()
    class GetCategoryError(error: Throwable) : TasksByCategoryEffect()
    class GetTasksError(error: Throwable) : TasksByCategoryEffect()
    class EditCategoryError(error: Throwable) : TasksByCategoryEffect()
    class DeleteCategoryError(error: Throwable) : TasksByCategoryEffect()
}