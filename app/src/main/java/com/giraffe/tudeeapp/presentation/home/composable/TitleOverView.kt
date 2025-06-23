package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TitleOverView(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 8.dp),
        text = stringResource(R.string.overview),
        color = Theme.color.title,
        style = Theme.textStyle.title.large
    )
}