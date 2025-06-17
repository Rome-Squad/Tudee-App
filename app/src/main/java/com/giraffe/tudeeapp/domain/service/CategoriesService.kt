package com.giraffe.tudeeapp.domain.service

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CategoriesService {

    fun getAllCategories(): Result<Flow<List<Category>>, DomainError>

    suspend fun getCategoryById(id: Long): Result<Category, DomainError>

    suspend fun createCategory(category: Category): Result<Long, DomainError>

    suspend fun updateCategory(category: Category): Result<Unit, DomainError>

    suspend fun deleteCategory(id: Long): Result<Unit, DomainError>
}