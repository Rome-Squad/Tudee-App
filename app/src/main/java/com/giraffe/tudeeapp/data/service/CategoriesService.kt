package com.giraffe.tudeeapp.data.service

import android.content.Context
import androidx.core.net.toUri
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.domain.entity.Category
import kotlinx.coroutines.flow.first

class CategoriesService(
    private val dao: CategoryDao,
    private val context: Context
) {

    suspend fun createDefaultCategoriesIfEmpty() {
        if (dao.getAllCategories().first().isNotEmpty()) return

        defaultCategoryNames.forEach { name ->
            val resId = getCategoryIcon(name)
            val uri = "android.resource://${context.packageName}/$resId".toUri().toString()
            val category = Category(
                name = name,
                imageUri = uri,
                isEditable = false,
                taskCount = 0
            ).toDto()
            dao.createCategory(category)
        }
    }


    private val defaultCategoryNames = listOf(
        "Education", "Adoration", "Family & friend", "Cooking", "Traveling", "Coding",
        "Fixing bugs", "Medical", "Shopping", "Agriculture", "Entertainment", "Gym",
        "Cleaning", "Work", "Event", "Budgeting", "Self-care"
    )

    private fun getCategoryIcon(categoryName: String): Int {
        return when (categoryName) {
            "Education" -> R.drawable.book_open
            "Adoration" -> R.drawable.quran
            "Family & friend" -> R.drawable.user_multiple
            "Cooking" -> R.drawable.chef
            "Traveling" -> R.drawable.airplane
            "Coding" -> R.drawable.developer
            "Fixing bugs" -> R.drawable.bug
            "Medical" -> R.drawable.hospital_location
            "Shopping" -> R.drawable.shopping_cart
            "Agriculture" -> R.drawable.plant
            "Entertainment" -> R.drawable.baseball
            "Gym" -> R.drawable.body_part_muscle
            "Cleaning" -> R.drawable.blush_brush
            "Work" -> R.drawable.briefcase
            "Event" -> R.drawable.birthday_cake
            "Budgeting" -> R.drawable.money_bag
            "Self-care" -> R.drawable.in_love
            else -> R.drawable.book_open
        }
    }


}