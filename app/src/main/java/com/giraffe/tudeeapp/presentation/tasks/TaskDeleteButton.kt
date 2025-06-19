package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TaskDeleteButton(
    onClick: () -> Unit,
    icon: Painter = painterResource(R.drawable.trash),
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
            painter = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(28.dp)
        )
    }
}


//@Preview
//@Composable
//fun TaskDeleteButtonPreview() {
//    TaskDeleteButton(
//        onClick = { /* Do nothing */ },
//        modifier = Modifier
//    )
//}