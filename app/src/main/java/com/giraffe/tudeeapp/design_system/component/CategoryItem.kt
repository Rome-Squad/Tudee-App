package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun CategoryItem(
    icon: Painter,
    categoryName: String,
    modifier: Modifier = Modifier,
    count: Int = 0,
    isShowCount: Boolean = true,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Box(
                Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(LocalTudeeColors.current.surfaceHigh)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = stringResource(R.string.category_icon),
                    modifier = Modifier
                        .padding(23.dp)
                        .size(32.dp),
                    contentScale = ContentScale.Crop
                )
            }
            if (isSelected) {
                Box(
                    Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(LocalTudeeColors.current.greenAccent)
                        .align(Alignment.TopEnd)
                        .padding(top = 2.dp, end = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.tick_double),
                        contentDescription = stringResource(R.string.checkmark_container)
                    )
                }
            } else if (isShowCount) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(LocalTudeeColors.current.surfaceLow)
                        .padding(vertical = 2.dp, horizontal = 10.5.dp)
                        .align(Alignment.TopEnd),
                ) {
                    Text(
                        text = count.toString(),
                        style = Theme.textStyle.label.small,
                        color = LocalTudeeColors.current.hint,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Text(
            text = categoryName,
            style = Theme.textStyle.label.small,
            color = LocalTudeeColors.current.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemWithCountPreview() {
    CategoryItem(
        icon = painterResource(R.drawable.book_open),
        categoryName = "Education",
        count = 16
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryItemWithSelectedPreview() {
    CategoryItem(
        icon = painterResource(R.drawable.book_open),
        categoryName = "Education",
        isSelected = true
    )
}