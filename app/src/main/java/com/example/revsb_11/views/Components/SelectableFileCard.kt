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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.revsb_11.R
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.ui.theme.AppColors
import com.example.revsb_11.ui.theme.AppTheme

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
            .widthIn(min = 35.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = 0.dp,
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .widthIn(min = 35.dp)
                .background(AppTheme.colors.secondaryElement),
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
                    tint = AppTheme.colors.background
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
    textColor: AppColors = AppTheme.colors
) {

    Box(
        modifier = modifier
            .clickable { onClick(selectedFile) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        ) {

            Text(
                modifier = Modifier,
                text = selectedFile.longTermPath,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                color = textColor.background,
            )

            Row (
                modifier = modifier
            ) {
                selectedFile.fileSize?.let { fileSize ->
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = fileSize,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        color = textColor.background,
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
                    color = textColor.background,
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