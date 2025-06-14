package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme


@Composable
fun TudeeButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    type: ButtonType = ButtonType.Primary,
    state: ButtonState = ButtonState.Normal,
    icon: Painter? = null,
    contentDescription: String? = null
) {
    val isEnabled = state != ButtonState.Disabled
    val buttonBackground = if(state== ButtonState.Disabled)Theme.color.disable  else Theme.color.primary
    val contentInDisableState = Theme.color.stroke

    val backgroundColor = when (type) {
        ButtonType.Primary -> if (state == ButtonState.Error) Theme.color.errorVariant else buttonBackground
        ButtonType.Secondary -> Theme.color.surface
        ButtonType.Text -> Color.Transparent
        ButtonType.FAB -> buttonBackground
    }

    val contentColor = when (type) {
        ButtonType.Primary -> if (state == ButtonState.Error) Theme.color.error else Theme.color.onPrimary
        ButtonType.Secondary -> Theme.color.primary
        ButtonType.Text -> if (state == ButtonState.Error) Theme.color.error else Theme.color.primary
        ButtonType.FAB -> if (state == ButtonState.Disabled) contentInDisableState else Theme.color.onPrimary
    }

    val shape = when (type) {
        ButtonType.FAB -> CircleShape
        else -> RoundedCornerShape(100.dp)
    }

    val applyAnimatedWidth = type != ButtonType.FAB
    val animatedWidth by animateDpAsState(
        targetValue = if (state == ButtonState.Loading && applyAnimatedWidth) 140.dp else Dp.Unspecified,
        label = "AnimatedButtonWidth"
    )


    val buttonModifier = modifier
        .then(if (type == ButtonType.FAB) Modifier.size(64.dp) else Modifier.height(56.dp)).width(130.dp)
        .then(if (applyAnimatedWidth) Modifier.widthIn(min = animatedWidth) else Modifier)
        .then( if (type==ButtonType.FAB) Modifier.shadow(elevation = 6.dp, shape = shape) else Modifier)
        .then( if(type==ButtonType.Secondary)Modifier.border(width = 1.dp, color = Theme.color.stroke ,shape=shape) else Modifier)


    Button(
        onClick = onClick,
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = Theme.color.disable,
            disabledContentColor = Theme.color.stroke
        ),
        enabled = isEnabled,
        shape = shape,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    ) {

        if (type != ButtonType.FAB) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = text, style = Theme.textStyle.label.large)

                if (state == ButtonState.Loading && icon!=null) {
                    Icon(icon, contentDescription, modifier = Modifier.size(24.dp))

                }
            }
        } else {
            // FAB
            if (state == ButtonState.Loading && icon!=null) {
                Icon(icon, contentDescription, modifier = Modifier.size(24.dp))

            } else if (icon != null) {
                Icon(icon, contentDescription, modifier = Modifier.size(24.dp))
            }
        }
    }

}

@Preview
@Composable
fun TudeeButtonsPreview() {
    MaterialTheme {


        TudeeButton(
            text = "Submit",
            onClick = {},
            type = ButtonType.Secondary,
            state = ButtonState.Disabled
           //, icon = painterResource(R.drawable.loading)
        )

        /* TudeeButton(
             text = "",
             onClick = {},
             type = ButtonType.FAB,
             state = ButtonState.Loading,
             icon =painterResource(R.drawable.dowenload),
             contentDescription = "Loading"
         )*/

    }
}

enum class ButtonType {
    Primary, Secondary, Text, FAB
}

enum class ButtonState {
    Normal, Loading, Disabled, Error
}