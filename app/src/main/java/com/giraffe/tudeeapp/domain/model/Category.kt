package com.giraffe.tudeeapp.domain.model

data class Category(
    val id: Long,
    val name: String,
    val imageUri: String,
    val isEditable: Boolean = true,
    val taskCount: Int = 0
)