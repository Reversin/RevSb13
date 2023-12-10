package com.example.revsb_11.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val primaryElement: Color,
    val secondaryElement: Color,
    val text: Color
)

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Teal700 = Color(0xFF018786)
val Green100 = Color(4, 195, 211)
val Green200 = Color(30, 190, 196)
val Black100 = Color(0xff1b1a17)
val White100 = Color(0xFFFFFFFF)
val Second = Color(0xffff8303)
val Third = Color(0xffa35709)
val Fourth = Color(0xffb91212)

val DarkMain = Color(72, 66, 109)
val DarkSecondary = Color(233, 229, 255)
val DarkAccent = Color(50, 65, 83)

val LightMain = Color(233, 229, 255)
val LightSecondary = Color(72, 66, 109)
val LightAccent = Color(50, 65, 83)

val Dark1 = Color(72, 66, 109)
val Dark2 = Color(49, 44, 81)
val Dark3 = Color(240, 195, 142)
val Dark4 = Color(241, 170, 155)

//val Light1 = Color()
private val DarkColorScheme = darkColorScheme(
    surface = DarkMain,
    secondary = DarkSecondary,
    primary = DarkAccent,
)

private val LightColorScheme = lightColorScheme(
    surface = LightMain,
    secondary = LightSecondary,
    primary = LightAccent,
)

internal fun currentAppColors(darkTheme: Boolean = true) : ColorScheme =
    if (darkTheme) { DarkColorScheme } else { LightColorScheme
}