package com.example.revsb_11.views.Components

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.revsb_11.dataclasses.SelectedFile
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon

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

                var dismissState = rememberDismissState(initialValue = DismissValue.Default)
                val contextForToast = LocalContext.current.applicationContext

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    // swiping from left to right
                    Toast.makeText(contextForToast, "Delete", Toast.LENGTH_SHORT).show()
                    onSwipeToDelete(fileItem)
                }

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.padding(vertical = 4.dp),
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = when (direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
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

