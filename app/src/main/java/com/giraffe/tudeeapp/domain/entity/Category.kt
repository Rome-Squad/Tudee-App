package com.giraffe.tudeeapp.domain.entity

data class Category(
    val id: Long = 0L,
    val name: String,
    val imageUri: String,
    val isEditable: Boolean,
    val taskCount: Int
)