package com.giraffe.tudeeapp.design_system.component.button_type

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color,
    disabledContentColor: Color,
    isLoading: Boolean = false,
    isDisable: Boolean = false
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isDisable) disabledContainerColor else containerColor,
        label = "ContainerColorAnimation"
    )

    val foregroundColor by animateColorAsState(
        targetValue = if (isDisable) disabledContentColor else contentColor,
        label = "ContentColorAnimation"
    )

    Button(
        onClick = onClick,
        enabled = !isDisable,
        shape = RoundedCornerShape(100.dp),
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = foregroundColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (text != null)
                Text(text, style = Theme.textStyle.label.large)
            if (isLoading)
                Icon(
                    painter = painterResource(R.drawable.loading),
                    contentDescription = stringResource(R.string.loading_indicator),
                    modifier = Modifier.size(24.dp)
                )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BasicButton(
        text = "hello",
        onClick = { },
        containerColor = Color.Blue,
        contentColor = Color.Yellow,
        disabledContainerColor = Color.Red,
        disabledContentColor = Color.White
    )
}