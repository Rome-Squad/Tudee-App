package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun NegativeButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDisable: Boolean = false,
    onClick: () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        containerColor = Theme.color.errorVariant,
        contentColor = Theme.color.error,
        disabledContainerColor = Theme.color.disable,
        disabledContentColor = Theme.color.stroke,
        isLoading = isLoading,
        isDisable = isDisable
    )
}


@Preview
@Composable
fun TudeeNegativeButtonsPreview() {
    TudeeTheme {
        NegativeButton(
            text = "Submit",
            onClick = {},
            isLoading = true
        )
    }
}