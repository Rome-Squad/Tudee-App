package com.giraffe.tudeeapp.presentation.categories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun CategoriesScreen() {
    Text(
        text = "Categories Screen",
        color = Theme.color.title,
        fontSize = 20.sp
    )
}