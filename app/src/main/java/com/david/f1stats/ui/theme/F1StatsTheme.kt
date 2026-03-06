package com.david.f1stats.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = F1Red,
    onPrimary = F1White,
    primaryContainer = F1DarkRed,
    onPrimaryContainer = F1White,
    background = F1DarkBackground,
    onBackground = F1White,
    surface = F1DarkSurface,
    onSurface = F1White,
    error = F1Red,
    onError = F1White
)

private val LightColorScheme = lightColorScheme(
    primary = F1Red,
    onPrimary = F1White,
    primaryContainer = F1DarkRed,
    onPrimaryContainer = F1White,
    background = F1LightBackground,
    onBackground = F1Black,
    surface = F1LightSurface,
    onSurface = F1Black,
    error = F1Red,
    onError = F1White
)

@Composable
fun F1StatsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = F1Typography,
        content = content
    )
}
