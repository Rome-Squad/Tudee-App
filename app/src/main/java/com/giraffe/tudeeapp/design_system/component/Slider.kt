package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun Slider(
    modifier: Modifier = Modifier,
    slider: Slider,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = slider.title,
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = slider.status,
                    contentDescription = "tudee status icon"
                )
            }
            Text(
                text = slider.subtitle,
                style = Theme.textStyle.body.small,
                color = Theme.color.body,
                maxLines = 2,
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(65.dp)
                .alpha(0.16f),
            painter = painterResource(R.drawable.blue_circle),
            contentDescription = "blue circle"
        )
        Image(
            painter = slider.image, contentDescription = "tudee image",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(7.dp)
                .size(width = 61.dp, height = 92.dp)
        )
    }
}

data class Slider(
    val image: Painter,
    val title: String,
    val subtitle: String,
    val status: Painter
)


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SliderPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.nothing_on_your_list),
            subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
            status = painterResource(R.drawable.tudde_emoji_empty_tasks),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}