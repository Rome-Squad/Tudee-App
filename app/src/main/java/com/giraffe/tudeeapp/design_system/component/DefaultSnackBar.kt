package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@Composable
fun DefaultSnackBar(
    modifier: Modifier = Modifier,
    snackState: SnackbarHostState = SnackbarHostState(),
) {
    SnackbarHost(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        hostState = snackState
    ) { snackBarData ->
        val isError = snackBarData.visuals.actionLabel != SnakeBarType.ERROR.name
        snackBarData.visuals.actionLabel
        Snackbar(
            modifier = Modifier.height(56.dp),
            containerColor = Theme.color.surfaceHigh,
            shape = RoundedCornerShape(16.dp)
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

enum class SnakeBarType {
    SUCCESS,
    ERROR,
}

@Preview(showBackground = true)
@Composable
fun TudeeSnackBarPreview() {
    TudeeTheme {
        DefaultSnackBar()
    }
}