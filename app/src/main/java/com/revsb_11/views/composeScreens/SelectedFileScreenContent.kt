package com.revsb_11.views.composeScreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revsb_11.viewmodels.SelectedFilesViewModel
import androidx.compose.material3.MaterialTheme.colorScheme
import com.revsb_11.views.components.DriveImageListScreen
import com.revsb_11.views.components.GoogleSignInButton

@Composable
fun SelectedFilesScreenContent(
    modifier: Modifier,
    viewModel: SelectedFilesViewModel? = null,
) {

    val getContent = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel?.fileHasBeenSelected(uri)
        }
    }

    val context = LocalContext.current


    val screenState =
        viewModel?.selectedFilesUIStateLiveData?.observeAsState()?.value

    val driveFolders = viewModel?.driveFoldersLiveData?.observeAsState(emptyList())

    screenState?.onFindFileButtonClicked?.getContentIfNotHandled()?.let {
        getContent.launch(arrayOf("*/*"))
    }
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
                backgroundColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ) {
            }

            if (viewModel != null) {
                DriveImageListScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                )
            }

//            if (screenState?.savedSelectedFilesList != null) {
//                SelectedFileList(
//                    modifier = Modifier.fillMaxSize(),
//                    files = driveFolders, //screenState.savedSelectedFilesList,
//                    onSelectedFileClicked = {},
//                    onEditButtonClicked = {},
//                    onSwipeToDelete = { selectedFile ->
//                        viewModel.onSwipeDeleteItem(selectedFile)
//                    },
//                )
//            }
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
//                color = White100
//            )
//        }

        //TODO:

        LoadFoldersButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 75.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            viewModel = viewModel
        )

        GoogleSignInButton(
            viewModel = viewModel,
            context = context,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun LoadFoldersButton(modifier: Modifier, viewModel: SelectedFilesViewModel?) {
    Button(
        modifier = modifier,
        onClick = {
            viewModel?.loadImages()
        },
    ) {

        Text(
            text = "Folders",
            color = colorScheme.onPrimary,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSelectedFilesScreenContent() {
    SelectedFilesScreenContent(
        modifier = Modifier,
    )
}