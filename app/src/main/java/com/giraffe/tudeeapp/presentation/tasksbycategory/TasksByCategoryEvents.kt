package com.giraffe.tudeeapp.presentation.tasksbycategory

import com.giraffe.tudeeapp.domain.util.DomainError

sealed class TasksByCategoryEvents {
    class CategoryDeleted : TasksByCategoryEvents()
    class CategoryEdited : TasksByCategoryEvents()
    class GetCategoryError(error: DomainError) : TasksByCategoryEvents()
    class GetTasksError(error: DomainError) : TasksByCategoryEvents()
    class EditCategoryError(error: DomainError) : TasksByCategoryEvents()
    class DeleteCategoryError(error: DomainError) : TasksByCategoryEvents()
}