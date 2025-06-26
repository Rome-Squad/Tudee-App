package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun NegativeTextButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDisable: Boolean = false,
    onClick: () -> Unit,
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        containerColor = Color.Transparent,
        contentColor = Theme.color.error,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Theme.color.stroke,
        isLoading = isLoading,
        isDisable = isDisable
    )
}


@Preview(showBackground = true)
@Composable
fun TudeeNegativeTextButtonsPreview() {
    TudeeTheme {
        NegativeTextButton(
            text = stringResource(R.string.negative_text_button),
            onClick = {},
            isLoading = true,
        )
    }
}