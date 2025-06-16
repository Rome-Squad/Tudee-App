package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun TudeeSecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean=false,
    isDisable: Boolean=false,
    icon: Painter? = null,
    onClick: () -> Unit,

    ) {
    val shape = RoundedCornerShape(100.dp)
    val animatedWidth by animateDpAsState(
        if (isLoading) 140.dp else Dp.Unspecified,
        label = ""
    )

    Button(
        onClick = onClick,
        enabled = !isDisable,
        shape = shape,
        modifier = modifier
            .height(56.dp)
            .widthIn(min = animatedWidth)
            .width(130.dp)
            .border(1.dp, Theme.color.stroke, shape),
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.color.surface,
            contentColor = Theme.color.primary,
            disabledContainerColor = Theme.color.disable,
            disabledContentColor = Theme.color.stroke
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, style = Theme.textStyle.label.large)
            if (isLoading && icon!=null) {
                Icon(
                    painterResource(R.drawable.loading),
                    "contentDescription",
                    Modifier.size(24.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun TudeeSecondryButtonsPreview() {
    TudeeTheme {
        TudeeSecondaryButton(
            text = "Submit",
            onClick = {},
            isLoading = true,
            isDisable = false
        )


    }
}