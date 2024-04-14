package com.revsb_11.views.components

import CustomCircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.revsb_11.models.dataclasses.SelectedFile

@Composable
fun DriveImageListScreen(
    modifier: Modifier,
    onEditIconClicked: (SelectedFile) -> Unit,
    files: List<SelectedFile>,
    loadError: String,
    isLoading: Boolean,
    isEndOfList: Boolean,
    loadImages: () -> Unit,
) {
    val filesList: MutableList<SelectedFile> = files.toMutableList()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(filesList) { index, file ->

            // Триггер для загрузки новых изображений
            if ((index >= filesList.size - 1) && !isLoading && !isEndOfList) {
                LaunchedEffect(key1 = "loadMore") {
                    loadImages()
                }
            }

            SelectableFileCard(
                modifier = modifier,
                file = file,
                onEditIconClicked = { onEditIconClicked(file) })
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(200.dp)) // Дополнительное пространство в конце списка
        }

        if (isLoading || isEndOfList) {
            // Отображаем индикатор загрузки как элемент списка
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    contentAlignment = Alignment.Center // Центрируем индикатор загрузки внутри Box
                ) {

                    CustomCircularProgressIndicator(modifier)
                }
            }
        }

        if (loadError.isNotEmpty()) {
            item {

                Text(
                    text = "Ошибка: $loadError",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}