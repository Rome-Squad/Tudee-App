package com.giraffe.tudeeapp.data.service

import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toEntityList
import com.giraffe.tudeeapp.data.util.mapExceptionToTudeeError
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CategoryServiceImp(
    private val categoryDao: CategoryDao
) : CategoriesService {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { list ->
            list.toEntityList()
        }.catch { e ->
            throw mapExceptionToTudeeError(e)
        }
    }

    override suspend fun getCategoryById(id: Long): Category {
        return safeCall {
            categoryDao.getCategoryById(id).toEntity()
        }
    }

    override suspend fun createCategory(category: Category): Long {
        return safeCall {
            val categoryEntity = category.toDto()
            categoryDao.createCategory(categoryEntity)
        }
    }

    override suspend fun updateCategory(category: Category) {
        return safeCall {
            val categoryEntity = category.toDto()
            categoryDao.updateCategory(categoryEntity)
        }
    }

    override suspend fun deleteCategory(id: Long) {
        return safeCall {
            categoryDao.deleteCategory(id)
        }
    }
}