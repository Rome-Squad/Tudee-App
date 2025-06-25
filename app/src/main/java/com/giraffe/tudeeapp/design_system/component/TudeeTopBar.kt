package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TudeeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    withOption: Boolean = false,
    label: String? = null,
    onClickBack: () -> Unit = {},
    onClickEdit: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CircleButton(
            iconRes = R.drawable.back_arrow,
            onClick = onClickBack
        )

        Column(modifier = Modifier.padding(start = 12.dp)) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp)
        ) {
            Text(
                text = title,
                color = Theme.color.title,
                style = Theme.textStyle.title.large,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            )

            if (label != null) {
                Text(
                    text = label,
                    color = Theme.color.hint,
                    style = Theme.textStyle.label.small,
                )
            }
        }

        Spacer(Modifier.weight(1f))
        AnimatedVisibility(withOption) {
            CircleButton(
                iconRes = R.drawable.pencil_edit_transparent,
                onClick = onClickEdit
        if (withOption)
            circleButton(
                iconRes = iconEditRes,
                onClick = onClickEdit,
                background = background,
                iconColor = iconColor,
                borderColor = borderColor,
            )
        }
    }
}

@Composable
fun CircleButton(
    iconRes: Int,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Theme.color.surfaceHigh)
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Theme.color.body
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TudeeTopBarPreview() {
    TudeeTopBar(
        title = "Tasks",
        withOption = true,
    )
}