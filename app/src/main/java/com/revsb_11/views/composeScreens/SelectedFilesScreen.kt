package com.revsb_11.views.composeScreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.revsb_11.utils.screenPaddings
import com.revsb_11.viewmodels.SelectedFilesViewModel
import com.revsb_11.views.composeScreens.effects.SelectedFilesScreenEffect

@Composable
fun SelectedFilesScreen(
    viewModel: SelectedFilesViewModel,
    navController: NavController
) {
    val effect by viewModel.effect.collectAsState()

    LaunchedEffect(effect) {
        when (effect) {
            is SelectedFilesScreenEffect.NavigateToAddCommentScreen -> {
                navController.navigate("add_comments_screen")
            }
            // Обработка других сайд-эффектов
            null -> viewModel.onScreenOpened()
        }
        viewModel.resetEffect()
    }

    SelectedFilesScreenContent(
        modifier = Modifier.screenPaddings(),
        viewModel = viewModel,
    )
}
