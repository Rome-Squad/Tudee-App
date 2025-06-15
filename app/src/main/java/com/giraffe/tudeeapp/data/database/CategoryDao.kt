package com.giraffe.tudeeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.data.util.Constants.CATEGORY_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME WHERE uid= :id")
    suspend fun getCategoryById(id: Long): CategoryEntity

    @Insert
    suspend fun createCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity): Unit

    @Query("DELETE From $CATEGORY_TABLE_NAME WHERE uid=:id")
    suspend fun deleteCategory(id: Long): Unit
}