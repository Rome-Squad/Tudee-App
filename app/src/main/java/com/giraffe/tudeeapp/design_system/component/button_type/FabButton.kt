package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@JvmOverloads
@Composable
fun FabButton(
    icon: Painter,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDisable: Boolean = false,
    onClick: () -> Unit
) {
    val shape = CircleShape

    Button(
        onClick = onClick,
        enabled = !isDisable,
        shape = shape,
        modifier = modifier
            .size(64.dp)
            .shadow(
                elevation = 4.dp,
                shape = shape,
                ambientColor = Color(0x1F000000),
                spotColor = Color(0x1F000000),
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.color.primary,
            contentColor = Theme.color.onPrimary,
            disabledContainerColor = Theme.color.disable,
            disabledContentColor = Theme.color.stroke
        ),
        contentPadding = PaddingValues(0.dp)
    ) {

        val layoutDirection = LocalLayoutDirection.current
        val isRtl = layoutDirection == LayoutDirection.Rtl
        if (isLoading)
            Icon(
                painterResource(R.drawable.loading),
                stringResource(R.string.loading_indicator),
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        scaleX = if (isRtl) -1f else 1f
                    }
            )
        else
            Icon(icon, null, modifier = Modifier)

    }
}

@Preview
@Composable
fun TudeeButtonsPreview() {
    TudeeTheme {
        FabButton(
            icon = painterResource(R.drawable.add_category),
            onClick = {},
            isLoading = true
        )
    }
}