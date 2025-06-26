package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    hint: String,
    icon: Painter,
    isReadOnly: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onClicked: () -> Unit = {},
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val borderColor by animateColorAsState(
        targetValue = if (isFocused && !isReadOnly) Theme.color.primary else Theme.color.stroke
    )
    val iconColor by animateColorAsState(
        targetValue = if (textValue.isBlank()) Theme.color.hint else Theme.color.body
    )
    LaunchedEffect(isFocused) {
        if (isFocused && isReadOnly) {
            onClicked()
            focusManager.clearFocus()
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                indication = null,
                interactionSource = null
            ) { onClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp),
            painter = icon,
            contentDescription = stringResource(R.string.user_icon),
            tint = iconColor
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 13.dp)
                .width(1.dp)
                .background(color = Theme.color.stroke)
                .align(Alignment.CenterVertically)
        )
        TextField(
            readOnly = isReadOnly,
            interactionSource = interactionSource,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            value = textValue,
            onValueChange = onValueChange,
            maxLines = 1,
            placeholder = {
                Text(
                    text = hint,
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
private fun Preview() {
    TudeeTheme {
        DefaultTextField(
            textValue = stringResource(R.string.text_value),
            hint = stringResource(R.string.hint),
            icon = painterResource(R.drawable.user),
        )
    }
}