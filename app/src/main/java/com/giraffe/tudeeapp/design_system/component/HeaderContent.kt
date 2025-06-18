package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun HeaderContent(title: String) {
    Text(
        text = title,
        style = Theme.textStyle.title.large,
        color = Theme.color.title,
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceHigh)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderContentPreview() {
    HeaderContent("Categories")
}