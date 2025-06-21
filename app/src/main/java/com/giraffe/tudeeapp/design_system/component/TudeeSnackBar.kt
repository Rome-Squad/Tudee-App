package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun TudeeSnackBar(
    modifier: Modifier = Modifier,
    message: String,
    iconRes: Int,
    backgroundColor: Color = Theme.color.surfaceHigh,
    textColor: Color = Theme.color.body,
    iconTintColor: Color = Theme.color.error,
    iconBackgroundColor: Color = Theme.color.errorVariant,
    durationMillis: Long = 3000L,
    onDismiss: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect (Unit) {
        visible = true
        delay(durationMillis)
        visible = false
        delay(1000)
        onDismiss()
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color.Black.copy(alpha = 0.12f)
                )
                .background(backgroundColor, RoundedCornerShape(16.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = iconTintColor,
                    modifier = Modifier.size(21.5.dp)
                )
            }

            Text(
                text = message,
                color = textColor,
                style = Theme.textStyle.body.medium
            )
        }
    }

}

@Composable
fun DefaultSnackBar(
    modifier: Modifier,
    snackState: SnackbarHostState,
    isError: Boolean,
    isRtl: Boolean = false,
) {
    SnackbarHost(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        hostState = snackState
    ) { snackBarData ->
        Snackbar(
            modifier = Modifier.height(56.dp),
            containerColor = Theme.color.surfaceHigh,
            shape = RoundedCornerShape(16.dp)
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (isError) Theme.color.errorVariant else Theme.color.greenVariant,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = if (isError) R.drawable.ic_error else R.drawable.ic_success),
                            contentDescription = null,
                            tint = if (isError) Theme.color.error else Theme.color.greenAccent,
                            modifier = Modifier.size(21.5.dp)
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = snackBarData.visuals.message,
                        color = Theme.color.body.copy(.6f),
                        style = Theme.textStyle.body.medium
                    )
                }
            }

        }
    }
}


data class TudeeSnackBarState(
    val message: String,
    val isError: Boolean = false
)


@Preview(showBackground = true)
@Composable
fun TudeeSnackBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        TudeeSnackBar(
            message = "Error message",
            iconRes = com.giraffe.tudeeapp.R.drawable.ic_error
        )

        TudeeSnackBar(
            message = "Success message",
            iconRes = com.giraffe.tudeeapp.R.drawable.ic_success,
            iconTintColor = Theme.color.greenAccent,
            iconBackgroundColor = Theme.color.greenVariant
        )
    }
}