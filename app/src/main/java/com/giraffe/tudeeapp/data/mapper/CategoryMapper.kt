package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.domain.model.Category


fun Category.toEntity(): CategoryEntity {

    return CategoryEntity(
        uid = this.id,
        name = this.name,
        imageUri = this.imageUri)

}

fun CategoryEntity.toCategory():Category{
    return Category(
        id = this.uid,
        name=this.name,
        imageUri=this.imageUri)
}