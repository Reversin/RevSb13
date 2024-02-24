package com.revsb_11.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    surface = DarkMain,
    secondary = DarkSecondary,
    primary = DarkAccent,

//    onSurface = Color.Red,
//    onPrimaryContainer = Color.Red,
//    background = Color.Red,
//    onBackground = Color.Red,
//
//    onPrimary = Color.Red,
//    primaryContainer = Color.Red,
//    onSecondary = Color.Red,
//    secondaryContainer = Color.Red,
//    onSecondaryContainer = Color.Red,
//    tertiary = Color.Red,
//    onTertiary = Color.Red,
//    tertiaryContainer = Color.Red,
//    onTertiaryContainer = Color.Red,
//    error = Color.Red,
//    errorContainer = Color.Red,
//    onError = Color.Red,
//    onErrorContainer = Color.Red,
//    surfaceVariant = Color.Red,
//    onSurfaceVariant = Color.Red,
//    outline = Color.Red,
//    inverseOnSurface = Color.Red,
//    inverseSurface = Color.Red,
//    inversePrimary = Color.Red,
//    surfaceTint = Color.Red,
//    outlineVariant = Color.Red,
//    scrim = Color.Red,
)

private val LightColorScheme = lightColorScheme(
    surface = LightMain,
    secondary = LightSecondary,
    primary = LightAccent,
)


@Composable
fun Rev_composeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}