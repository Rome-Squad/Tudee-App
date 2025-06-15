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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertBottomSheet(
    modifier: Modifier = Modifier,
    title: String = "Delete category",
    subTitle: String = "Are you sure to continue?",
    imgRes: Int = R.drawable.sure_robot,
    redBtnTitle: String = "Delete",
    blueBtnTitle: String = "Cancel",
    onRedBtnClick: () -> Unit = {},
    onBlueBtnClick: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            showBottomSheet = false
        },
        sheetState = sheetState
    ) {
        Column {
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
                contentDescription = "robot",
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
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) showBottomSheet = false
                            }
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
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) showBottomSheet = false
                            }
                    }) {
                    Text(text = blueBtnTitle, style = Theme.textStyle.label.large)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme {
        AlertBottomSheet()
    }
}