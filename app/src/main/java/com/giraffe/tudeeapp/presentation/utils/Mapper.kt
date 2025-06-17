package com.giraffe.tudeeapp.presentation.utils

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

fun CategoryUi.toEntity() = Category(
    id = id,
    name = name,
    imageUri = imageUri
)