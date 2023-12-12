package com.example.revsb_11.views.composeScreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.revsb_11.ui.theme.White100
import com.example.revsb_11.viewmodels.SelectedFilesViewModel
import com.example.revsb_11.views.Components.SelectedFileList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectedFilesScreenContent(
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

    val findFileButtonClickEvent =
        viewModel?.onFindFileButtonClickedLiveData?.observeAsState()?.value
    findFileButtonClickEvent?.getContentIfNotHandled()?.let {
        getContent.launch(arrayOf("*/*"))
    }

    val savedFilesList = viewModel?.savedSelectedFilesListLiveData?.observeAsState()?.value
    val buttonClicked = viewModel?.test?.observeAsState("")?.value

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 1.dp),
            verticalArrangement = Arrangement.Absolute.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            if (savedFilesList != null) {
                SelectedFileList(
                    modifier = Modifier.fillMaxSize(),
                    files = savedFilesList,
                    onSelectedFileClicked = {},
                    onEditButtonClicked = {},
                    onSwipeToDelete = { selectedFile ->
                        viewModel.onSwipeDeleteItem(selectedFile)
                    },
                )
            }

            if (buttonClicked == "555") {
                getContent.launch(arrayOf("*/*"))
            }

        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            onClick = { viewModel?.onFindFileButtonClicked() },
        ) {

            Text(
                text = "Find file",
                color = White100
            )
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