package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDisable: Boolean = false,
    onClick: () -> Unit
) {

    BasicButton(
        onClick = onClick,
        modifier = modifier.border(1.dp, Theme.color.stroke, RoundedCornerShape(100.dp)),
        text = text,
        containerColor = Color.Transparent,
        contentColor = Theme.color.primary,
        disabledContainerColor = Theme.color.disable,
        disabledContentColor = Theme.color.stroke,
        isLoading = isLoading,
        isDisable = isDisable
    )
}


@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    TudeeTheme {
        SecondaryButton(
            text = "Submit",
            onClick = {},
            isLoading = true
        )
    }
}