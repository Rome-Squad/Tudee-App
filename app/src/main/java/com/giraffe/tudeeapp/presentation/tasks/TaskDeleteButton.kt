package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TaskDeleteButton(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Delete,
    tint: Color = Theme.color.error,
    contentDescription: String? = "Delete Task",
    backgroundColor: Color = Theme.color.errorVariant,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}