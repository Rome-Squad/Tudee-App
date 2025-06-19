package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.tudeeapp.design_system.theme.Theme

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