package com.giraffe.tudeeapp.presentation.home

import android.R.attr.icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.color.lightThemeColor
import com.giraffe.tudeeapp.design_system.component.Slider
import com.giraffe.tudeeapp.design_system.component.TudeeAppBar
import com.giraffe.tudeeapp.design_system.text_style.defaultTextStyle
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun HomeScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var width by remember { mutableStateOf(0) }
        val heightInDp = with(LocalDensity.current) { (width * 0.2f).toDp() }
        TudeeAppBar(
            modifier = Modifier
                .onGloballyPositioned {
                    width = it.size.width
                }
                .height(heightInDp),
            isDarkTheme = false,
            onThemeSwitchToggle = {}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(lightThemeColor.primary),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(lightThemeColor.surface)
                            .padding(top = 250.dp)
                    ) {
                        //اكتب باقي الاسكرين هناا
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(281.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .background(lightThemeColor.surfaceHigh)
                        .padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
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
                                text = "today, 22 Jun 2025",
                                color = lightThemeColor.body,
                                fontFamily = defaultTextStyle.label.medium.fontFamily
                            )
                        }
                        Slider()

                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, top = 8.dp),
                            text = "Overview",
                            color = Theme.color.title,
                            style = Theme.textStyle.title.large
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .height(112.dp)
                                    .background(Theme.color.greenAccent)
                                    .weight(1f)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(38.dp),
                                    painter = painterResource(id = R.drawable.oval_copy),
                                    tint = Theme.color.onPrimary.copy(alpha = 0.87f),
                                    contentDescription = "calendar_icon",
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp, top = 12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(38.dp),
                                        painter = painterResource(id = R.drawable.overview_card_icon_container__2_),
                                        tint = Theme.color.onPrimary,
                                        contentDescription = "overview_card_icon_container__2_",
                                    )
                                    Text(
                                        text = "2",
                                        color = Theme.color.onPrimary,
                                        style = Theme.textStyle.headline.medium
                                    )
                                    Text(
                                        text = "Done",
                                        color = Theme.color.onPrimaryCaption,
                                        style = Theme.textStyle.label.small
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .height(112.dp)
                                    .background(lightThemeColor.greenAccent)
                                    .weight(1f)
                            ) {

                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .height(112.dp)
                                    .background(lightThemeColor.greenAccent)
                                    .weight(1f)
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    widthDp = 360,
)
@Composable
fun Preview() {
    HomeScreen()
}