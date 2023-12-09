package com.example.revsb_11.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

object AppTheme {

    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}
private val LocalAppColors = staticCompositionLocalOf {
    currentAppColors()
}

