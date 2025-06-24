package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    isDisable: Boolean = false,
    onClick: () -> Unit
) {

    val backgroundBrush = if (isDisable) {
        SolidColor(Theme.color.disable)
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Theme.color.primaryGradient.startGradient,
                Theme.color.primaryGradient.endGradient
            )
        )
    }

    BasicButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundBrush, RoundedCornerShape(100.dp)),
        text = text,
        containerColor = Color.Transparent,
        contentColor = Theme.color.onPrimary,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Theme.color.stroke,
        isLoading = isLoading,
        isDisable = isDisable
    )
}


@Preview
@Composable
fun PrimaryButtonPreview() {
    TudeeTheme {
        PrimaryButton(
            text = "Submit",
            onClick = {}
        )
    }
}