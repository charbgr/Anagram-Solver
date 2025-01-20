package com.github.charbgr.anagramsolver.designsystem.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * A Material [ColorScheme] implementation which sets all colors to [debugColor] to discourage usage
 * of [MaterialTheme.colorScheme] in preference to [BgTheme.colors].
 */
internal fun discourageMaterialColors(
    debugColor: Color = Color.Green,
): ColorScheme = ColorScheme(
    primary = debugColor,
    onPrimary = debugColor,
    primaryContainer = debugColor,
    onPrimaryContainer = debugColor,
    inversePrimary = debugColor,
    secondary = debugColor,
    onSecondary = debugColor,
    secondaryContainer = debugColor,
    onSecondaryContainer = debugColor,
    tertiary = debugColor,
    onTertiary = debugColor,
    tertiaryContainer = debugColor,
    onTertiaryContainer = debugColor,
    background = debugColor,
    onBackground = debugColor,
    surface = debugColor,
    onSurface = debugColor,
    surfaceVariant = debugColor,
    onSurfaceVariant = debugColor,
    surfaceTint = debugColor,
    inverseSurface = debugColor,
    inverseOnSurface = debugColor,
    error = debugColor,
    onError = debugColor,
    errorContainer = debugColor,
    onErrorContainer = debugColor,
    outline = debugColor,
    outlineVariant = debugColor,
    scrim = debugColor,
    surfaceBright = debugColor,
    surfaceDim = debugColor,
    surfaceContainer = debugColor,
    surfaceContainerHigh = debugColor,
    surfaceContainerHighest = debugColor,
    surfaceContainerLow = debugColor,
    surfaceContainerLowest = debugColor,
)

/**
 * A Material [Typography] implementation which sets all textStyles to [fontSize] to discourage
 * usage of [MaterialTheme.typography] in preference to [BgTheme.typography].
 */
internal fun discourageMaterialTypography(
    fontSize: TextUnit = 64.sp,
): Typography = Typography(
    displayLarge = TextStyle(fontSize = fontSize),
    displayMedium = TextStyle(fontSize = fontSize),
    displaySmall = TextStyle(fontSize = fontSize),
    headlineLarge = TextStyle(fontSize = fontSize),
    headlineMedium = TextStyle(fontSize = fontSize),
    headlineSmall = TextStyle(fontSize = fontSize),
    titleLarge = TextStyle(fontSize = fontSize),
    titleMedium = TextStyle(fontSize = fontSize),
    titleSmall = TextStyle(fontSize = fontSize),
    bodyLarge = TextStyle(fontSize = fontSize),
    bodyMedium = TextStyle(fontSize = fontSize),
    bodySmall = TextStyle(fontSize = fontSize),
    labelLarge = TextStyle(fontSize = fontSize),
)