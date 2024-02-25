package com.revsb_11.views.composeScreens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import com.revsb_11.ui.theme.White100
import com.revsb_11.viewmodels.SelectedFilesViewModel

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
                .fillMaxSize()
                .padding(top = 1.dp),
            verticalArrangement = Arrangement.Absolute.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            if (viewModel != null) {
                ImageListScreen(viewModel)
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


        SignInButton(
            viewModel = viewModel,
            context = context,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
        )


        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 75.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                viewModel?.loadImages()
            },
        ) {

            Text(
                text = "Folders",
                color = White100,
            )
        }
    }
}

@Composable
fun SignInButton(viewModel: SelectedFilesViewModel?, context: Context, modifier: Modifier) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel?.setGoogleAccount(account)
                // Теперь вы можете вызвать getFolders()
            } catch (e: ApiException) {
                // Обработка ошибки входа в систему
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = {
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope(DriveScopes.DRIVE))  // Запрос на полный доступ к Google Drive
                .build()

            val signInClient = GoogleSignIn.getClient(context, signInOptions)
            val signInIntent = signInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }) {
        Text(
            text = "Войти в Google",
            color = White100
        )
    }
}


@Composable
fun ImageListScreen(viewModel: SelectedFilesViewModel) {
    val images by viewModel.images.observeAsState(emptyList())
    val loadError by viewModel.loadError.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEndOfList by viewModel.endOfList.observeAsState(false)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(images) { index, image ->
            if ((index >= images.size - 1) && !isLoading && !isEndOfList) {
                LaunchedEffect(key1 = true) {
                    viewModel.loadImages()  // Загрузка следующей страницы изображений
                }
            }
            Text(text = image)
        }

        if (isLoading && !isEndOfList) {
            item {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
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


@Preview(showBackground = true)
@Composable
fun PreviewSelectedFilesScreenContent() {
    SelectedFilesScreenContent(
        modifier = Modifier,
    )
}