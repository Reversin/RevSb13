package com.revsb_11.views.composeScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.request.ImageRequest
import com.revsb_11.R
import com.revsb_11.viewmodels.AddFileCommentsViewModel
import com.revsb_11.views.components.ZoomableImage
import com.revsb_11.views.composeScreens.effects.AddCommentScreenEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFileCommentsScreenContent(
    modifier: Modifier,
    viewModel: AddFileCommentsViewModel? = null,
) {
    if (viewModel != null) {

        val file = viewModel.fileLiveData.observeAsState().value

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(file?.fileThumbnail) //TODO: разораться и заменить на ViewLink
            .crossfade(true)
            .build()

        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopAppBar(
                    title = { Text("Заголовок") },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.triggerEffect(AddCommentScreenEffect.BackToPreviousScreen)
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_back), // Замените на ваш ресурс иконки
                                contentDescription = "Назад"
                            )
                        }
                    }
                )

                ZoomableImage(
                    modifier = modifier.fillMaxSize(),
                    imageRequest = imageRequest,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddFileCommentsScreenContent() {
    AddFileCommentsScreenContent(
        modifier = Modifier,
    )
}