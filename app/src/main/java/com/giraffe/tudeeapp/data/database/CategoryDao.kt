package com.giraffe.tudeeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.data.util.Constants.CATEGORY_TABLE_NAME
import com.giraffe.tudeeapp.data.util.Constants.TASK_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("""
        SELECT c.uid, c.name, c.imageUri, c.isEditable, COUNT(t.uid) as taskCount
        FROM $CATEGORY_TABLE_NAME c
        LEFT JOIN $TASK_TABLE_NAME t ON c.uid = t.categoryId
        GROUP BY c.uid
        """)
    fun getAllCategories(): Flow<List<CategoryDto>>

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME WHERE uid= :id")
    suspend fun getCategoryById(id: Long): CategoryDto

    @Insert
    suspend fun createCategory(category: CategoryDto): Long

    @Update
    suspend fun updateCategory(category: CategoryDto)

    @Transaction
    suspend fun deleteCategory(categoryId: Long) {
        deleteCategoryById(categoryId)
        deleteTasksByCategory(categoryId)
    }

    @Query("DELETE From $CATEGORY_TABLE_NAME WHERE uid=:categoryId")
    suspend fun deleteCategoryById(categoryId: Long)

    @Query("DELETE From $TASK_TABLE_NAME WHERE categoryId=:categoryId")
    suspend fun deleteTasksByCategory(categoryId: Long)
}