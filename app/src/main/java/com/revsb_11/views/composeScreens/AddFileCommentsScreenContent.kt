package com.revsb_11.views.composeScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.revsb_11.R
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.viewmodels.AddFileCommentsViewModel
import com.revsb_11.viewmodels.AddFileCommentsViewModel.Companion.CONFIRM_BACK_DIALOG_TYPE
import com.revsb_11.viewmodels.AddFileCommentsViewModel.Companion.TIMEOUT_DIALOG_TYPE
import com.revsb_11.views.components.ExpandingTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFileCommentsScreenContent(
    modifier: Modifier,
    viewModel: AddFileCommentsViewModel? = null,
) {


    val addFileCommentsUIState = viewModel?.addFileCommentsUIStateLiveData?.observeAsState()?.value


    val selectedFile = addFileCommentsUIState?.selectedFile ?: SelectedFile()

    val imageBitmap = viewModel?.bitmapLiveData?.observeAsState()?.value

    val errorTimeoutEvent = viewModel?.errorTimeoutEventLiveData?.observeAsState(false)?.value

    val showConfirmBackDialog = viewModel?.showConfirmBackDialogLiveData?.observeAsState()?.value


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
                        viewModel?.onBackClick()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back), // Замените на ваш ресурс иконки
                            contentDescription = "Назад"
                        )
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .weight(3f),
                contentAlignment = Alignment.Center,
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = "Loaded Image",
                        contentScale = ContentScale.Inside,
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedFile.fileThumbnail),
                        contentDescription = "Изображение в низком разрешение чтобы не ожидать загрузки",
                        contentScale = ContentScale.Inside,
                    )  // Отображается, пока изображение не загружено
                }
            }

            ExpandingTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .weight(2f),
                text = selectedFile.fileComments,
                onTextChanged = { changedText ->
                    viewModel?.onTextHasBeenChanged(changedText)
                }
            )

            if (errorTimeoutEvent == true) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.dismissDialog(
                            TIMEOUT_DIALOG_TYPE
                        )
                    },
                    text = { Text("Превышенно время ожидания загрузки. Повторить загрузку изображения?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.loadImage();
                                viewModel.dismissDialog(
                                    TIMEOUT_DIALOG_TYPE
                                )
                            }
                        ) {
                            Text(stringResource(id = R.string.yes))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { viewModel.onBackClick(true) }
                        ) {
                            Text(stringResource(id = R.string.no))
                        }
                    }
                )
            }
            if (showConfirmBackDialog == true) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissDialog(CONFIRM_BACK_DIALOG_TYPE) },
                    text = { Text(stringResource(id = R.string.exit_without_saving)) },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.onBackClick(true); viewModel.dismissDialog(
                                CONFIRM_BACK_DIALOG_TYPE
                            )
                            }
                        ) {
                            Text(stringResource(id = R.string.yes))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { viewModel.dismissDialog(CONFIRM_BACK_DIALOG_TYPE) }
                        ) {
                            Text(stringResource(id = R.string.no))
                        }
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddFileCommentsScreenContent() {

    AddFileCommentsScreenContent(
        modifier = Modifier.fillMaxSize(),
    )
}