package com.revsb_11.views.components

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revsb_11.models.dataclasses.SelectedFile
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.Icons
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedFileList(
    modifier: Modifier,
    files: List<SelectedFile>,
    onSelectedFileClicked: (SelectedFile) -> Unit,
    onEditButtonClicked: (SelectedFile) -> Unit,
    onSwipeToDelete: (SelectedFile) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = files,
                key = {
                    it.longTermPath
                }) {fileItem ->

                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == EndToStart) {
                    // swiping from left to right
                    onSwipeToDelete(fileItem)
                }

                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier.padding(vertical = 4.dp),
                    backgroundContent = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismissBox
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                Settled -> Color.LightGray
                                StartToEnd -> Color.Red
                                EndToStart -> Color.Red
                            }, label = ""
                        )
                        val alignment = when (direction) {
                            StartToEnd -> Alignment.CenterStart
                            EndToStart -> Alignment.CenterEnd
                            Settled -> Alignment.Center
                        }
                        val icon = when (direction) {
                            StartToEnd -> Icons.Default.Delete
                            EndToStart -> Icons.Default.Delete
                            Settled -> null
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == Settled) 0.75f else 1f, label = ""
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            if (icon != null) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        }
                    },
                    content = {
                        SelectableFileCard(
                            modifier = Modifier,
                            selectedFile = fileItem,
                            onSelectedFileClicked = onSelectedFileClicked,
                            onEditButtonClicked = onEditButtonClicked,
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewColumnElement() {
    SelectedFileList(
        modifier = Modifier,
        files = listOf(
            SelectedFile(
                filePath = "Yes",
                fileSize = "Yes",
                longTermPath = "Yes",
                fileComments = "Yes"
            ),
            SelectedFile(
                filePath = "No",
                fileSize = "No",
                longTermPath = "No",
                fileComments = "No",
            )
        ),
        onSelectedFileClicked = {},
        onEditButtonClicked = {},
        onSwipeToDelete = {},
    )
}

