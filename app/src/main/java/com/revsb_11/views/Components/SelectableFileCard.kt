package com.revsb_11.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revsb_11.R
import com.revsb_11.models.dataclasses.SelectedFile

@Composable
fun SelectableFileCard(
    modifier: Modifier,
    selectedFile: SelectedFile,
    onSelectedFileClicked: (SelectedFile) -> Unit,
    onEditButtonClicked: (SelectedFile) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .widthIn(min = 35.dp)
            .padding(horizontal = 9.dp),
        shape = RoundedCornerShape(32.dp),
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .widthIn(min = 35.dp)
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            SelectedFileCardContent(
                modifier = modifier.weight(10f),
                selectedFile = selectedFile,
                onClick = onSelectedFileClicked,
            )
            IconButton(
                onClick = { onEditButtonClicked },
                modifier = modifier
                    .weight(2f)
                    .padding(end = 15.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = "stringResource(id = R.string.content_description_placeholder)",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectableFileCardPreview() {
    SelectableFileCard(
        modifier = Modifier,
        selectedFile = SelectedFile(
            filePath = "Yes",
            fileSize = "Yes",
            longTermPath = "YesdsadascsbuscucubcbocbDOUBOCBSOCBSIUCBIubiuBIuiubsvvosdovubsdouvovbsdvidbsviusbavbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
            fileComments = "Yes"
        ),
        onSelectedFileClicked = {},
        onEditButtonClicked = {},
    )
}