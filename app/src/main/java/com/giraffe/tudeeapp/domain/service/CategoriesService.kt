package com.giraffe.tudeeapp.domain.service

import com.giraffe.tudeeapp.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesService {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Long): Category

    suspend fun createCategory(category: Category): Long

    suspend fun updateCategory(category: Category): Unit

    suspend fun deleteCategory(id: Long): Unit
}