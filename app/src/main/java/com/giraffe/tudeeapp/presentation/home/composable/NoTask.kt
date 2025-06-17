package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun NoTask(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Image(
            modifier = Modifier
                .size(149.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.circles_icone),
            contentDescription = stringResource(R.string.task_card_image_background_description),
        )
        Image(
            modifier = Modifier
                .size(width = 107.dp, height = 103.dp)
                .padding(bottom = 3.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.sure_robot),
            contentDescription = stringResource(R.string.calendar_icon),
        )

        Column(
            modifier = Modifier
                .background(
                    Theme.color.surfaceHigh,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 2.dp
                    )
                )
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)

        ) {
            Text(
                modifier = Modifier
                    .width(179.dp),
                text = stringResource(R.string.no_tasks),
                color = Theme.color.body,
                style = Theme.textStyle.title.small
            )
            Text(
                modifier = Modifier
                    .width(183.dp),
                text = stringResource(R.string.add_your_first_task),
                color = Theme.color.hint,
                style = Theme.textStyle.body.small
            )
        }
    }
}


@Preview(widthDp = 360)
@Composable
fun NoTaskPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF9F9F9)),
    ) {
        NoTask()
    }
}