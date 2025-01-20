package com.github.charbgr.anagramsolver.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.charbgr.anagramsolver.designsystem.theme.colors.ColorPalette
import com.github.charbgr.anagramsolver.designsystem.theme.colors.ColorScheme
import com.github.charbgr.anagramsolver.designsystem.theme.colors.discourageMaterialColors
import com.github.charbgr.anagramsolver.designsystem.theme.colors.discourageMaterialTypography

private val DarkColorScheme = ColorScheme(
    primary = ColorPalette.Purple80,
    secondary = ColorPalette.PurpleGrey80,
    tertiary = ColorPalette.Pink80
)

private val LightColorScheme = ColorScheme(
    primary = ColorPalette.Purple40,
    secondary = ColorPalette.PurpleGrey40,
    tertiary = ColorPalette.Pink40
)

private val LocalColorScheme = staticCompositionLocalOf<ColorScheme> {
    error("No ColorScheme provided")
}

@Composable
@Suppress("LongParameterList")
private fun ProvideBgThemeAttributes(
    colorScheme: ColorScheme,
    content: @Composable () -> Unit,
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colorScheme.copy()
    }
    colorPalette.update(colorScheme)
    CompositionLocalProvider(
        LocalColorScheme provides colorPalette,
        content = content,
    )
}

@Composable
fun AppTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme: ColorScheme = if (isSystemInDarkTheme) DarkColorScheme else LightColorScheme

    ProvideBgThemeAttributes(
        colorScheme = colorScheme,
    ) {
        MaterialTheme(
            colorScheme = discourageMaterialColors(),
            typography = discourageMaterialTypography(),
            content = content,
        )
    }
}

object AppTheme {

    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
}
