package com.revsb_11.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.revsb_11.models.dataclasses.SelectedFile
import io.github.mataku.middleellipsistext.MiddleEllipsisText

@Composable
fun SelectedFileCardContent(
    modifier: Modifier,
    selectedFile: SelectedFile,
    onClick: (SelectedFile) -> Unit,
) {
    val textColor: Color = MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier
            .clickable { onClick(selectedFile) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        ) {

            selectedFile.longTermPath.let {
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