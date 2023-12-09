package com.example.revsb_11.views.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.dataclasses.SelectedFilesUIState

@Composable
fun SelectedFileList(
    modifier: Modifier,
    uiState: SelectedFilesUIState,
    onSelectedFileClicked: (SelectedFile) -> Unit,
    onEditButtonClicked: (SelectedFile) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(uiState.files) { file ->
                SelectableFileCard(
                    modifier = Modifier.fillMaxWidth(),
                    selectedFile = file,
                    onSelectedFileClicked = onSelectedFileClicked,
                    onEditButtonClicked = onEditButtonClicked,
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
        uiState = SelectedFilesUIState(
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
        ),
        onSelectedFileClicked = {},
        onEditButtonClicked = {},
    )
}

