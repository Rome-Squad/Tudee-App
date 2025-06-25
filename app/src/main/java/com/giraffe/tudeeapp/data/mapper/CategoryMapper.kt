package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.domain.entity.Category


fun Category.toDto(): CategoryDto {
    return CategoryDto(
        uid = this.id,
        name = this.name,
        imageUri = this.imageUri,
        isEditable = this.isEditable,
        taskCount = this.taskCount
    )

}

fun CategoryDto.toEntity():Category{
    return Category(
        id = this.uid,
        name=this.name,
        imageUri=this.imageUri,
        isEditable = this.isEditable,
        taskCount = this.taskCount
    )
}