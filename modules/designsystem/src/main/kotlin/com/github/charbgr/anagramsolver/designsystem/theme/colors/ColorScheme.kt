package com.github.charbgr.anagramsolver.designsystem.theme.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
@Suppress("LongParameterList")
class ColorScheme(
    primary: Color,
    secondary: Color,
    tertiary: Color,
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var tertiary by mutableStateOf(tertiary)
        private set

    fun update(other: ColorScheme) {
        primary = other.primary
        secondary = other.secondary
        tertiary = other.tertiary
    }

    fun copy(): ColorScheme = ColorScheme(
        primary = primary,
        secondary = secondary,
        tertiary = tertiary,
    )
}