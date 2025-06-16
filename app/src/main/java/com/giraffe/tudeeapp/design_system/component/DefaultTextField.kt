package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    textValue: String = "hello",
    onValueChange: (String) -> Unit = {},
    hint: String = "Full name",
    iconRes: Int = R.drawable.ic_user
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = if (isFocused) Theme.color.primary else Theme.color.stroke,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = "user icon",
            tint = if (textValue.isBlank()) Theme.color.hint else Theme.color.body
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 13.dp)
                .fillMaxHeight()
                .width(1.dp)
                .background(color = Theme.color.stroke)
        )
        TextField(
            modifier = Modifier.weight(1f),
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

@Preview(showBackground = false)
@Composable
private fun Preview() {
    DefaultTextField()
}