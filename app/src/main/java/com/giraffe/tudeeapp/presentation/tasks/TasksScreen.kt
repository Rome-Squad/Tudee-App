package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TaskScreen() {
    Text(
        text = "Task Screen",
        color = Theme.color.title,
        fontSize = 20.sp
    )
}