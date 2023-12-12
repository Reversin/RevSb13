package com.example.revsb_11.views.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.revsb_11.R
import com.example.revsb_11.dataclasses.SelectedFile
import io.github.mataku.middleellipsistext.MiddleEllipsisText

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
        elevation = 0.dp,
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

@Composable
fun SelectedFileCardContent(
    modifier: Modifier,
    selectedFile: SelectedFile,
    onClick: (SelectedFile) -> Unit,
    textColor: Color = MaterialTheme.colorScheme.surface
) {

    Box(
        modifier = modifier
            .clickable { onClick(selectedFile) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        ) {

            selectedFile.filePathWithName?.let {
                MiddleEllipsisText(
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor,
                )
            }

            Row (
                modifier = modifier
            ) {
                selectedFile.fileSize?.let { fileSize ->
                    Text(
                        modifier = Modifier
                            .weight(2f),
                        text = fileSize,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        color = textColor,
                    )
                }

                Text(
                    modifier = Modifier
                        .weight(5f),
                    text = selectedFile.fileComments,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    color = textColor
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
            filePathWithName = "YesdsadascsbuscucubcbocbDOUBOCBSOCBSIUCBIubiuBIuiubsvvosdovubsdouvovbsdvidbsviusbavbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
            fileSize = "Yes",
            longTermPath = "YesdsadascsbuscucubcbocbDOUBOCBSOCBSIUCBIubiuBIuiubsvvosdovubsdouvovbsdvidbsviusbavbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
            fileComments = "Yes"
        ),
        onSelectedFileClicked = {},
        onEditButtonClicked = {},
    )
}