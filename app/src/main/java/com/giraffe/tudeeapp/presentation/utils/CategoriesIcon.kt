package com.giraffe.tudeeapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun categoriesIconBlurColor(categoryName: String): Color {
    return when (categoryName) {
        "Education" -> Theme.color.purpleAccent
        "Adoration" -> Theme.color.primary
        "Family & friend" -> Theme.color.secondary
        "Cooking" -> Theme.color.pinkAccent
        "Traveling" -> Theme.color.yellowAccent
        "Coding" -> Theme.color.purpleAccent
        "Fixing bugs" -> Theme.color.pinkAccent
        "Medical" -> Theme.color.primary
        "Shopping" -> Theme.color.secondary
        "Agriculture" -> Theme.color.greenAccent
        "Entertainment" -> Theme.color.yellowAccent
        "Gym" -> Theme.color.primary
        "Cleaning" -> Theme.color.greenAccent
        "Work" -> Theme.color.secondary
        "Event" -> Theme.color.pinkAccent
        "Budgeting" -> Theme.color.purpleAccent
        "Self-care" -> Theme.color.yellowAccent
        else -> Theme.color.purpleAccent
    }
}

fun categoriesIcon(categoryName: String): Int {
    return when (categoryName) {
        "Education" -> R.drawable.book_open_icon
        "Adoration" -> R.drawable.quran_icon
        "Family & friend" -> R.drawable.user_multiple_icon
        "Cooking" -> R.drawable.chef
        "Traveling" -> R.drawable.airplane_01
        "Coding" -> R.drawable.developer
        "Fixing bugs" -> R.drawable.bug_01
        "Medical" -> R.drawable.hospital_location
        "Shopping" -> R.drawable.shopping_cart_02
        "Agriculture" -> R.drawable.plant_02
        "Entertainment" -> R.drawable.baseball
        "Gym" -> R.drawable.body_part_muscle
        "Cleaning" -> R.drawable.blush_brush_02
        "Work" -> R.drawable.briefcase_05
        "Event" -> R.drawable.birthday_cake_icon
        "Budgeting" -> R.drawable.money_bag_01
        "Self-care" -> R.drawable.in_love
        else -> R.drawable.book_open_icon
    }
}