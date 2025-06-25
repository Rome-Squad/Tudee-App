package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    imgRes: Int,
    redBtnTitle: String,
    blueBtnTitle: String,
    isVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit = {},
    onRedBtnClick: () -> Unit = {},
    onBlueBtnClick: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    if (isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            containerColor = Theme.color.surface,
            onDismissRequest = {
                onVisibilityChange(false)
                onBlueBtnClick()
            },
            sheetState = sheetState
        ) {
            Content(
                title = title,
                subTitle = subTitle,
                imgRes = imgRes,
                redBtnTitle = redBtnTitle,
                blueBtnTitle = blueBtnTitle,
                onVisibilityChange = onVisibilityChange,
                onRedBtnClick = onRedBtnClick,
                onBlueBtnClick = onBlueBtnClick,
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    imgRes: Int,
    redBtnTitle: String,
    blueBtnTitle: String,
    onVisibilityChange: (Boolean) -> Unit = {},
    onRedBtnClick: () -> Unit = {},
    onBlueBtnClick: () -> Unit = {},
) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.color.title
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
            text = subTitle,
            style = Theme.textStyle.body.large,
            color = Theme.color.body
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
            painter = painterResource(imgRes),
            contentDescription = stringResource(R.string.sure_robot),
        )
        Column(
            modifier = Modifier
                .background(color = Theme.color.surfaceHigh)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Theme.color.errorVariant,
                    contentColor = Theme.color.error,
                ),
                shape = RoundedCornerShape(100.dp),
                contentPadding = PaddingValues(18.5.dp),
                onClick = {
                    onRedBtnClick()
                    onVisibilityChange(false)
                }) {
                Text(text = redBtnTitle, style = Theme.textStyle.label.large)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Theme.color.surfaceHigh,
                    contentColor = Theme.color.primary,
                ),
                shape = RoundedCornerShape(100.dp),
                contentPadding = PaddingValues(18.5.dp),
                border = BorderStroke(width = 1.dp, color = Theme.color.stroke),
                onClick = {
                    onBlueBtnClick()
                    onVisibilityChange(false)
                }) {
                Text(text = blueBtnTitle, style = Theme.textStyle.label.large)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TudeeTheme {
        Content(
            title = stringResource(R.string.delete_task),
            subTitle = stringResource(R.string.are_you_sure_to_continue),
            imgRes = R.drawable.sure_robot,
            redBtnTitle = stringResource(R.string.delete),
            blueBtnTitle = stringResource(R.string.cancel),
        )
    }
}