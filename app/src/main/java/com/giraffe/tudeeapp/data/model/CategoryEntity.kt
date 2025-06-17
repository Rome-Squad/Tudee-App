package com.giraffe.tudeeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.tudeeapp.data.util.Constants

@Entity(tableName = Constants.CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    val name:String,
    val imageUri: String,
    val isEditable: Boolean,
    val taskCount: Int
)
