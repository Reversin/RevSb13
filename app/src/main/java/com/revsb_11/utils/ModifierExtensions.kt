package com.revsb_11.utils

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier

fun Modifier.screenPaddings(): Modifier =
    this
        .systemBarsPadding()
        .navigationBarsPadding()