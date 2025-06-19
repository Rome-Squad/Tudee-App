package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TaskScreen(currentTabIndex: Int) {
    Text(
        text = "Tasks -> $currentTabIndex",
        color = Color.Red,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxSize(),
        textAlign = TextAlign.Center
    )
}