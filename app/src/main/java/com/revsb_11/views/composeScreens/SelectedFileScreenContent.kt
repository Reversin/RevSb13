package com.revsb_11.views.composeScreens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revsb_11.viewmodels.SelectedFilesViewModel
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.revsb_11.R
import com.revsb_11.views.components.DriveImageListScreen
import com.revsb_11.views.composeScreens.effects.SelectedFilesScreenEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedFilesScreenContent(
    modifier: Modifier,
    viewModel: SelectedFilesViewModel? = null,
) {
    if (viewModel != null) {

        val getContent = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null) {
                viewModel.fileHasBeenSelected(uri)
            }
        }

        val signInLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.initDriveRepository(account)
                    if (viewModel.isDriveAccessGranted()) {
                        viewModel.loadImages()
                    } else {
                        // Обработка ошибки доступа
                    }
                } catch (e: ApiException) {
                    // Обработка ошибки входа
                }
            }
        }

        val files = viewModel.images.observeAsState(emptyList()).value

        val screenState =
            viewModel.selectedFilesUIStateLiveData.observeAsState().value

        val driveFolders = viewModel.driveFoldersLiveData.observeAsState(emptyList())

        screenState?.onFindFileButtonClicked?.getContentIfNotHandled()?.let {
            getContent.launch(arrayOf("*/*"))
        }

        val alertMessage =
            viewModel.alertMessage.observeAsState().value

        val loadError = viewModel.loadError.observeAsState("").value
        val isLoading = viewModel.isLoading.observeAsState(false).value
        val isEndOfList = viewModel.endOfList.observeAsState(false).value

        val usingLocalStorage = false
        //    SignInWithGoogle(modifier)
        //    val context = LocalContext.current
        //    Toast.makeText(context, "${getDriveService()!=null}", Toast.LENGTH_SHORT).show()

        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopAppBar(
                    title = { Text(stringResource(R.string.fTitle_name)) },
                )

                if (usingLocalStorage) {
                    if (screenState?.savedSelectedFilesList != null) {
                        //                    SelectedFileList(
                        //                        modifier = Modifier.fillMaxSize(),
                        //                        files = screenState.savedSelectedFilesList,
                        //                        onSelectedFileClicked = {},
                        //                        onEditButtonClicked = {},
                        //                        onSwipeToDelete = { selectedFile ->
                        //                            viewModel.onSwipeDeleteItem(selectedFile)
                        //                        },
                        //                    )
                    }
                } else {
                    DriveImageListScreen(
                        modifier = modifier,
                        files = files,
                        onEditIconClicked = { selectedFile ->
                            viewModel.onEditFileCommentClick(selectedFile)
                        },
                        loadError = loadError,
                        isLoading = isLoading,
                        isEndOfList = isEndOfList,
                        loadImages = { viewModel.loadImages() },
                    )
                }
            }
            //        Button(
            //            modifier = Modifier
            //                .fillMaxWidth()
            //                .padding(top = 8.dp, bottom = 16.dp)
            //                .padding(horizontal = 16.dp)
            //                .align(Alignment.BottomCenter),
            //            onClick = { viewModel?.onFindFileButtonClicked() },
            //        ) {
            //
            //            Text(
            //                text = "Find file",
            //                color = Color.White
            //            )
            //        }

            //TODO:

            Button(modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 134.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.triggerEffect(SelectedFilesScreenEffect.NavigateToAddCommentScreen)
                }) {
                Text("Перейти к экрану 2")
            }

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 75.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.loadImages()
                },
            ) {
                Text(
                    text = "Folders",
                    color = colorScheme.onPrimary,
                )
            }

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    val googleSignInClient = viewModel.onSignInButtonClicked()
                    signInLauncher.launch(googleSignInClient.signInIntent)
                }) {
                Text(
                    text = "Войти в Google",
                    color = colorScheme.onPrimary
                )
            }



            alertMessage?.let {
                AlertDialog(
                    onDismissRequest = {
                        // Сбросьте сообщение об ошибке при закрытии диалога
                        viewModel.updateAlertMessage(null)
                    },
                    title = { Text(text = "Alert") },
                    text = { Text(text = stringResource(it)) },
                    confirmButton = {
                        Button(onClick = {
                            // Сбросьте сообщение об ошибке при подтверждении диалога
                            viewModel.updateAlertMessage(null)
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedFilesScreenContent() {
    SelectedFilesScreenContent(
        modifier = Modifier,
    )
}