package com.giraffe.tudeeapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giraffe.tudeeapp.data.model.Category
import com.giraffe.tudeeapp.data.util.Constants.CATEGORY_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME WHERE uid= :id")
    suspend fun getCategoryById(id: Long): Category

    @Insert
    suspend fun createCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category): Unit

    @Query("DELETE From $CATEGORY_TABLE_NAME WHERE uid=:id")
    suspend fun deleteCategory(id: Long): Unit
}