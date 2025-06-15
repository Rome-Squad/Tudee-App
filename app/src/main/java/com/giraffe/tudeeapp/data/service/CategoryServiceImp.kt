package com.giraffe.tudeeapp.data.service

import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toCategory
import com.giraffe.tudeeapp.data.mapper.toCategoryEntity
import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryServiceImp(   private val categoryDao: CategoryDao
) : CategoriesService {
    override fun getAllCategories(): Flow<Result<List<Category>, DomainError>> {
        return flow {
            try {
                categoryDao.getAllCategories()
                    .collect { list ->
                        val tasks = list.map { it.toCategory()}
                        emit(Result.Success(tasks))
                    }
                     //   emit(Result.Success(list))

            } catch (e: Throwable) {
                emit(Result.Error(error(e)))
            }
        }
    }

    override suspend fun getCategoryById(id: Long): Result<Category, DomainError> {
        return safeCall {
            categoryDao.getCategoryById(id).toCategory()
        }    }

    override suspend fun createCategory(category: Category): Result<Long, DomainError> {
        return safeCall {
            val categoryEntity=category.toCategoryEntity()
            categoryDao.createCategory(categoryEntity)
        }    }

    override suspend fun updateCategory(category: Category): Result<Unit, DomainError> {
        return safeCall {
            val categoryEntity=category.toCategoryEntity()
             categoryDao.updateCategory(categoryEntity)
        }}

    override suspend fun deleteCategory(id: Long): Result<Unit, DomainError> {
        return safeCall {
            categoryDao.deleteCategory(id)
        }    }
}