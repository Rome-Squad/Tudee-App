package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean=false,
    isDisable: Boolean=false,
    onClick: () -> Unit,
) {

    val startColor by animateColorAsState(
        targetValue = if (isDisable) Theme.color.disable else Theme.color.primaryGradient.startGradient,
        label = "StartGradientAnimation"
    )

    val endColor by animateColorAsState(
        targetValue = if (isDisable) Theme.color.disable else Theme.color.primaryGradient.endGradient,
        label = "EndGradientAnimation"
    )

    val backgroundBrush = if (isDisable) {
        SolidColor(startColor)
    } else {
        Brush.verticalGradient(
            colors = listOf(startColor, endColor)
        )
    }

    val contentColor = Theme.color.onPrimary
    val shape = RoundedCornerShape(100.dp)

    val animatedWidth by animateDpAsState(
        targetValue = if (isLoading) 140.dp else Dp.Unspecified,
        label = "WidthAnimation"
    )
    Box(
        modifier = modifier
            .height(56.dp)
            .widthIn(min = animatedWidth)
            .fillMaxWidth()
            .background(
                backgroundBrush,
                shape = shape
            )
    ) {
        Button(
            onClick = onClick,
            enabled = !isDisable,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Theme.color.stroke
            ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            modifier = Modifier
                .matchParentSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text, style = Theme.textStyle.label.large)
                if (isLoading)
                    Icon(
                        painter = painterResource(R.drawable.loading),
                        contentDescription = R.string.loading.toString(),
                        modifier = Modifier.size(24.dp)
                    )
            }
        }
    }
}


@Preview
@Composable
fun TudeePrimaryButtonsPreview() {
    TudeeTheme {
        PrimaryButton(
            text = "Submit",
            onClick = {},
            isLoading = false, isDisable = false,


            )


    }
}