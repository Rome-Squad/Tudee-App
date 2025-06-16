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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun Slider(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    subtitle: String,
    status: Painter,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .height(95.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                start = 12.dp,
                top = 12.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = status,
                    contentDescription = "tudee status icon"
                )
            }
            Text(
                text = subtitle,
                style = Theme.textStyle.body.small,
                color = Theme.color.body,
                maxLines = 2,
            )
        }
        Image(
            painter = image, contentDescription = "tudee image",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(72.61.dp)
                .height(108.dp)
                .offset(y = (-5).dp)
                .padding(end = 8.39.dp)
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SliderPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
//        Slider()
        Spacer(modifier = Modifier.height(20.dp))
    }
}