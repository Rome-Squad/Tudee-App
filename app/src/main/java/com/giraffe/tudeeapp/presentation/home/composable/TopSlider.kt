package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.lightThemeColor
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle
import com.giraffe.tudeeapp.presentation.util.getTodayDate

@Composable
fun TopSlider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.calendar_icon),
            tint = lightThemeColor.body,
            contentDescription = "calendar_icon",
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = getTodayDate(),
            color = lightThemeColor.body,
            fontFamily = defaultTextStyle.label.medium.fontFamily
        )
    }
}