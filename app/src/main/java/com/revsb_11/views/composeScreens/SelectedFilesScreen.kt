package com.revsb_11.views.composeScreens

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.revsb_11.utils.screenPaddings
import com.revsb_11.viewmodels.SelectedFilesViewModel

@Composable
fun SelectedFilesScreen(
    viewModel: SelectedFilesViewModel,
    navController: NavController
) {
    val context = LocalContext.current as ComponentActivity

    viewModel.onScreenOpened()


    SelectedFilesScreenContent(
        modifier = Modifier.screenPaddings(),
        viewModel = viewModel,
    )
}
