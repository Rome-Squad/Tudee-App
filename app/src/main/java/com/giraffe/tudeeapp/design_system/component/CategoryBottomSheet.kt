package com.giraffe.tudeeapp.design_system.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.button_type.PrimaryButton
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.add_new_category),
    categoryToEdit: Category? = null,
    onVisibilityChange: (Boolean) -> Unit = {},
    onAddClick: (Category) -> Unit = {},
    onEditClick: (Category) -> Unit = {},
    onDeleteClick: (Category) -> Unit = {},
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var categoryTitle by remember { mutableStateOf(categoryToEdit?.name ?: "") }
    var photoUri: Uri? by remember { mutableStateOf(categoryToEdit?.imageUri?.toUri()) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                photoUri = it
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    ModalBottomSheet(
        modifier = modifier,
        containerColor = Theme.color.surface,
        onDismissRequest = {
            onVisibilityChange(false)
        },
        sheetState = sheetState
    ) {

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = title,
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title
                )
                if (categoryToEdit != null) {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = { onDeleteClick(categoryToEdit) }),
                        text = stringResource(R.string.delete),
                        style = Theme.textStyle.label.large,
                        color = Theme.color.error
                    )
                }
            }

            DefaultTextField(
                modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                textValue = categoryTitle,
                onValueChange = { categoryTitle = it },
                hint = stringResource(R.string.category_title),
                iconRes = R.drawable.categories_unselected,
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.category_image),
                style = Theme.textStyle.title.medium,
                color = Theme.color.title
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(112.dp)
                    .height(113.dp)
                    .background(
                        color = if (photoUri == null) Theme.color.surface else Color.Black.copy(
                            .1f
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .dashedBorder(
                        color = Theme.color.stroke,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (photoUri == null && categoryToEdit?.imageUri == null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(R.drawable.ic_add_image),
                            contentDescription = "add image",
                            tint = Theme.color.hint
                        )
                        Text(
                            text = stringResource(R.string.upload),
                            style = Theme.textStyle.label.medium,
                            color = Theme.color.hint
                        )
                    }
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = photoUri ?: categoryToEdit?.imageUri)
                                .build()
                        ),
                        contentDescription = "selected photo"
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = Theme.color.surfaceHigh,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.padding(6.dp),
                            painter = painterResource(R.drawable.ic_pen),
                            contentDescription = "edit image",
                            tint = Theme.color.secondary
                        )

                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(color = Theme.color.surfaceHigh)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (categoryToEdit != null) stringResource(R.string.sava) else stringResource(
                        R.string.add
                    ),
                    isDisable = categoryTitle.isBlank() || photoUri == null
                ) {
                    if (categoryToEdit != null) {
                        onEditClick(
                            categoryToEdit.copy(
                                name = categoryTitle,
                                imageUri = photoUri.toString(),
                            )
                        )
                    } else {
                        onAddClick(
                            Category(
                                name = categoryTitle,
                                imageUri = photoUri.toString(),
                                taskCount = 0,
                                isEditable = true
                            )
                        )
                    }
                }
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.cancel)
                ) {
                    onVisibilityChange(false)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TudeeTheme {
        CategoryBottomSheet()
    }
}

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, density = this)
    val dashedStroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx())
        )
    )
    drawContent()
    drawOutline(
        outline = outline,
        style = dashedStroke,
        brush = SolidColor(color)
    )
}
