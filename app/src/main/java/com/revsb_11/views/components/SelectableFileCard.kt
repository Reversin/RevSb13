package com.revsb_11.views.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revsb_11.models.dataclasses.SelectedFile
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.revsb_11.models.dataclasses.DragAnchors
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectableFileCard(
    modifier: Modifier,
    file: SelectedFile,
//    onSelectedFileClicked: (SelectedFile) -> Unit,
    onEditIconClicked: () -> Unit,
) {

    val density = LocalDensity.current
    val defaultActionSize = 50.dp
    val actionSizePx = with(density) { defaultActionSize.toPx() }
    //val endActionSizePx = with(density) { (defaultActionSize + 50.dp).toPx() }
    val startActionSizePx = with(density) { defaultActionSize.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Start at -startActionSizePx
                DragAnchors.Center at 0f
                DragAnchors.End at 0f //endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }


    Card(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(3f / 4f)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary)
        ) {

            EditAction(
                modifier = Modifier
                    .width(defaultActionSize)
                    .offset {
                        IntOffset(
                            ((-state
                                .requireOffset() - actionSizePx))
                                .roundToInt(),
                            10,
                        )
                    },
                onClick = { onEditIconClicked() },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .offset {
                        IntOffset(
                            x = -state
                                .requireOffset()
                                .roundToInt(),
                            y = 0,
                        )
                    }
                    .anchoredDraggable(state, Orientation.Horizontal, reverseDirection = true),
            ) {
                Column(modifier = modifier.background(MaterialTheme.colorScheme.tertiaryContainer)) {

                    Image(
                        painter = rememberAsyncImagePainter(model = file.fileThumbnail),
                        contentDescription = "Описание изображения",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(2f),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center

                    ) {
                        Text(
                            text = file.fileName,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 8.dp, vertical = 1.5.dp)
                        )
                        Text(
                            text = file.fileSize,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 8.dp, vertical = 1.5.dp),
                            fontSize = 11.sp,
                        )
                    }

                }
            }

        }

//        endAction = {
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .align(Alignment.CenterEnd),
//
//                ) {
//                EditAction(
//                    Modifier
//                        .width(defaultActionSize)
//                        .fillMaxHeight()
//                        .offset {
//                            IntOffset(
//                                ((-state
//                                    .requireOffset()) + actionSizePx)
//                                    .roundToInt(), 0
//                            )
//                        }
//                )
//                DeleteAction(
//                    Modifier
//                        .width(defaultActionSize)
//                        .fillMaxHeight()
//                        .offset {
//                            IntOffset(
//                                ((-state
//                                    .requireOffset() * 0.5f) + actionSizePx)
//                                    .roundToInt(), 0
//                            )
//                        }
//                )
//            }
//        },
//        content = {
//            SelectedFileCardContent(modifier, file)
//        }

    }

}


@Preview
@Composable
private fun SelectableFileCardPreview() {
    SelectableFileCard(
        modifier = Modifier.size(width = 240.dp, height = 320.dp),
        file = SelectedFile(
            fileName = "FileName",
            fileSize = "FileSize",
        ),
//        onSelectedFileClicked = {},
        onEditIconClicked = {},
    )
}