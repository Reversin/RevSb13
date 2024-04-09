package com.revsb_11.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revsb_11.R

@Composable
fun SaveAction(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = modifier
                .size(48.dp)
                .padding(8.dp),
                onClick = {
                }) {
                Text("Перейти к экрану 2")
            }

            Text(
                text = "Save",
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }

}


@Composable
fun EditAction(
    modifier: Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            onClick = { onClick() },
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_write_comment),
                contentDescription = "Иконка",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Composable
fun DeleteAction(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
                    .padding(horizontal = 20.dp)
                    .size(22.dp),
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = "Delete",
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }

}

@Preview
@Composable
private fun SaveActionPreview() {
    SaveAction(
        modifier = Modifier,
    )
}

@Preview
@Composable
private fun EditActionPreview() {
    EditAction(
        modifier = Modifier,
        onClick = {}
    )
}

@Preview
@Composable
private fun DeleteActionPreview() {
    DeleteAction(
        modifier = Modifier,
    )
}