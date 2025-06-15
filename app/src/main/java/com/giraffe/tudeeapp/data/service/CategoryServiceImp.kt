package com.giraffe.tudeeapp.data.service

import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryServiceImp(private val categoryDao: CategoryDao){


    fun getAllCategories(): Flow<List<Category>> =categoryDao.getAllCategories()

    suspend fun getCategoryById(id: Long): Category = categoryDao.getCategoryById(id)

    suspend fun createCategory(category: Category): Long = categoryDao.createCategory(category)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(id: Long) = categoryDao.deleteCategory(id)


}