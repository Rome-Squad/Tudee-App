package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun ParagraphTextField(
    modifier: Modifier = Modifier,
    textValue: String? = null,
    hint: String? = null,
    onValueChange: (String) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Theme.color.primary else Theme.color.stroke
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(168.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        TextField(
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxSize(),
            value = textValue ?: "",
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hint ?: stringResource(R.string.hint),
                    style = Theme.textStyle.label.medium,
                    color = Theme.color.hint
                )
            },
            textStyle = Theme.textStyle.body.medium,
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedTextColor = Theme.color.body,
                unfocusedTextColor = Theme.color.body,
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ParagraphTextFieldPreview() {
    TudeeTheme {
        ParagraphTextField()
    }
}