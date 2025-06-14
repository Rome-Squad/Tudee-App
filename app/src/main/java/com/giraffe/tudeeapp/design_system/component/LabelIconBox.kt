package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.LocalTudeeColors
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle

@Composable
fun LabelIconBox(
    backgroundColor: Color = LocalTudeeColors.current.surfaceLow,
    contentColor: Color = LocalTudeeColors.current.hint,
    icon: Painter,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.priority_icon),
            tint = contentColor,
            modifier = Modifier
                .size(12.dp)
        )
        Text(
            text = label,
            style = defaultTextStyle.label.small,
            color = contentColor,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}