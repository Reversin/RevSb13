package com.revsb_11.views.composeScreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.revsb_11.utils.screenPaddings
import com.revsb_11.viewmodels.AddFileCommentsViewModel
import com.revsb_11.views.composeScreens.effects.AddCommentScreenEffect

@Composable
fun AddFileCommentsScreen(
    viewModel: AddFileCommentsViewModel,
    navController: NavController
) {

    val effect by viewModel.effect.collectAsState()

    LaunchedEffect(effect) {
        when (effect) {
            is AddCommentScreenEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
            // Обработка других сайд-эффектов
            null -> viewModel.onScreenOpened()
        }
        viewModel.resetEffect()
    }

    AddFileCommentsScreenContent(
        modifier = Modifier.screenPaddings(),
        viewModel = viewModel,
    )
}