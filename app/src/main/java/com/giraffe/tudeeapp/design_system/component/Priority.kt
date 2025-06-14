package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors


@Composable
fun HighPriority(
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    BaseBox(
        icon = painterResource(R.drawable.flag_icon),
        label = stringResource(R.string.high),
        backgroundColor = if (isSelected) LocalTudeeColors.current.pinkAccent else LocalTudeeColors.current.surfaceLow,
        contentColor = if (isSelected) LocalTudeeColors.current.onPrimary else LocalTudeeColors.current.hint,
        onClick = onClick
        )
}

@Composable
fun MediumPriority(
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    BaseBox(
        icon = painterResource(R.drawable.alert_icon),
        label = stringResource(R.string.medium),
        backgroundColor = if (isSelected) LocalTudeeColors.current.yellowAccent else LocalTudeeColors.current.surfaceLow,
        contentColor = if (isSelected) LocalTudeeColors.current.onPrimary else LocalTudeeColors.current.hint,
        onClick = onClick
    )
}

@Composable
fun LowPriority(
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    BaseBox(
        icon = painterResource(R.drawable.trade_down_icon),
        label = stringResource(R.string.low),
        backgroundColor = if (isSelected) LocalTudeeColors.current.greenAccent else LocalTudeeColors.current.surfaceLow,
        contentColor = if (isSelected) LocalTudeeColors.current.onPrimary else LocalTudeeColors.current.hint,
        onClick = onClick
    )
}


@Preview(showBackground = true)
@Composable
fun PriorityPreview() {
    Column {
        HighPriority(isSelected = true)
        Spacer(Modifier.height(10.dp))
        HighPriority(isSelected = false)
        Spacer(Modifier.height(10.dp))
        MediumPriority(isSelected = true)
        Spacer(Modifier.height(10.dp))
        MediumPriority(isSelected = false)
        Spacer(Modifier.height(10.dp))
        LowPriority(isSelected = true)
        Spacer(Modifier.height(10.dp))
        LowPriority(isSelected = false)
    }
}