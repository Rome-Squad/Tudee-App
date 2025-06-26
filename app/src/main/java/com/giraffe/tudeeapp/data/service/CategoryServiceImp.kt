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
    override suspend fun getAllCategories(): Flow<List<Category>> {
        if (categoryDao.getCategoriesCount() <= 0) addDefaultCategories()
        return categoryDao.getAllCategories()
            .map { list ->
                list.toEntityList()
            }.catch { e ->
                throw mapExceptionToTudeeError(e)
            }
    }

    private suspend fun addDefaultCategories() {
        val basePath = "file:///android_asset/categories/"
        listOf(
            "Education" to basePath + "book.png",
            "Adoration" to basePath + "quran.png",
            "Family & friend" to basePath + "social.png",
            "Cooking" to basePath + "chef.png",
            "Traveling" to basePath + "plane.png",
            "Coding" to basePath + "code.png",
            "Fixing bugs" to basePath + "bug.png",
            "Medical" to basePath + "hospital.png",
            "Shopping" to basePath + "cart.png",
            "Agriculture" to basePath + "plant.png",
            "Entertainment" to basePath + "baseball.png",
            "Gym" to basePath + "gym.png",
            "Cleaning" to basePath + "brush.png",
            "Work" to basePath + "bag.png",
            "Event" to basePath + "cake.png",
            "Budgeting" to basePath + "money_bag.png",
            "Self-care" to basePath + "in_love.png",
        ).forEach {
            val newCategory = Category(
                name = it.first,
                imageUri = it.second,
                isEditable = false,
                taskCount = 0
            )
            categoryDao.createCategory(newCategory.toDto())
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