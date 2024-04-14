package com.revsb_11.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ZoomableImage(modifier: Modifier = Modifier, fileContentLink: String?) {
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(fileContentLink)
//            // Здесь можно добавить различные настройки, например обработчики ошибок или плейсхолдеры
//            .crossfade(true)
//            .build()
//    )
//
//    Box(modifier = modifier) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(fileContentLink)
//                .crossfade(true)
//                .build(),
//            contentDescription = "Web Image",
//            modifier = modifier
//        )
//
//        if (painter.state is AsyncImagePainter.State.Loading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .size(40.dp)
//                    .align(Alignment.Center)
//            )
//        }
//    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(fileContentLink)
            .crossfade(true)
            .build(),
        contentDescription = "Remote Image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
