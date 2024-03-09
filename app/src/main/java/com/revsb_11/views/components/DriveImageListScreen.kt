package com.revsb_11.views.components

import CustomCircularProgressIndicator
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.revsb_11.viewmodels.SelectedFilesViewModel

@Composable
fun DriveImageListScreen(modifier: Modifier, viewModel: SelectedFilesViewModel) {
    val files by viewModel.images.observeAsState(emptyList())
    val loadError by viewModel.loadError.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEndOfList by viewModel.endOfList.observeAsState(false)

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(files) { index, file ->
            // Триггер для загрузки новых изображений
            if ((index >= files.size - 1) && !isLoading && !isEndOfList) {
                LaunchedEffect(key1 = "loadMore") {
                    viewModel.loadImages()
                }
            }

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Red)
                    .size(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = file.thumbnailLink),
                    contentDescription = "Описание изображения",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
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
                    CustomCircularProgressIndicator(modifier = modifier) // Пример размера индикатора
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