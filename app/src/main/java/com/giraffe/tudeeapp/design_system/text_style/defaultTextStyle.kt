package com.giraffe.tudeeapp.design_system.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.giraffe.tudeeapp.R

private val TudeeFontFamily = FontFamily(
    Font(R.font.nunito, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
)

val defaultTextStyle = TudeeTextStyle(
    headline = SizedTextStyle(
        large = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 30.sp
        ),
        medium = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        small = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )
    ),
    title = SizedTextStyle(
        large = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),
        medium = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        small = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    ),
    body = SizedTextStyle(
        large = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        medium = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        small = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 17.sp
        )
    ),
    label = SizedTextStyle(
        large = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 19.sp
        ),
        medium = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 17.sp
        ),
        small = TextStyle(
            fontFamily = TudeeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    )
)
