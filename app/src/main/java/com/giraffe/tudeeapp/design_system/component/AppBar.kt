package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TudeeAppBar(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    onThemeSwitchToggle: () -> Unit,
    background: Color = Theme.color.primary,
    contentColor: Color = Theme.color.onPrimary
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp, 48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.4f))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tudee),
                    contentDescription = "Tudee Logo",
                    contentScale = ContentScale.FillBounds,
                )
            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.tudee),
                    style = Theme.textStyle.body.large.copy(
                        fontFamily = FontFamily(
                            Font(R.font.cherry_bomb)
                        )
                    ),
                    color = contentColor
                )

                Text(
                    text = stringResource(R.string.app_tagline),
                    style = Theme.textStyle.label.small,
                    color = contentColor
                )
            }

        }

        ThemeSwitch(
            modifier = Modifier
                .padding(vertical = 5.dp),
            switchSize = 72.dp,
            isDarkTheme = isDarkTheme,
            padding = 2.dp,
        ) {
            onThemeSwitchToggle()
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TudeeAppBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        TudeeAppBar(
            modifier = Modifier
                .systemBarsPadding(),
            onThemeSwitchToggle = {},
            isDarkTheme = false
        )

        TudeeAppBar(
            modifier = Modifier,
            onThemeSwitchToggle = {},
            isDarkTheme = true
        )
    }
}