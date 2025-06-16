package com.giraffe.tudeeapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun categoriesIcon(categoryName: String): CategoryResource {
    return when (categoryName) {
        "Education" -> CategoryResource(R.drawable.book_open_icon, Theme.color.purpleAccent)
        "Adoration" -> CategoryResource(R.drawable.quran_icon, Theme.color.primary)
        "Family & friend" -> CategoryResource(R.drawable.user_multiple_icon, Theme.color.secondary)
        "Cooking" -> CategoryResource(R.drawable.chef, Theme.color.pinkAccent)
        "Traveling" -> CategoryResource(R.drawable.airplane_01, Theme.color.yellowAccent)
        "Coding" -> CategoryResource(R.drawable.developer, Theme.color.purpleAccent)
        "Fixing bugs" -> CategoryResource(R.drawable.bug_01, Theme.color.pinkAccent)
        "Medical" -> CategoryResource(R.drawable.hospital_location, Theme.color.primary)
        "Shopping" -> CategoryResource(R.drawable.shopping_cart_02, Theme.color.secondary)
        "Agriculture" -> CategoryResource(R.drawable.plant_02, Theme.color.greenAccent)
        "Entertainment" -> CategoryResource(R.drawable.baseball, Theme.color.yellowAccent)
        "Gym" -> CategoryResource(R.drawable.body_part_muscle, Theme.color.primary)
        "Cleaning" -> CategoryResource(R.drawable.blush_brush_02, Theme.color.greenAccent)
        "Work" -> CategoryResource(R.drawable.briefcase_05, Theme.color.secondary)
        "Event" -> CategoryResource(R.drawable.birthday_cake_icon, Theme.color.pinkAccent)
        "Budgeting" -> CategoryResource(R.drawable.money_bag_01, Theme.color.purpleAccent)
        "Self-care" -> CategoryResource(R.drawable.in_love, Theme.color.yellowAccent)
        else -> CategoryResource(R.drawable.book_open_icon, Theme.color.purpleAccent)
    }
}


data class CategoryResource(
    val categoryImage: Int,
    val blurColor: Color
)