package com.leggomymeggos.marvelcompose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtraThemeColors(
    val tertiary: Color,
    val onTertiary: Color
)

val LocalExtraThemeColors = staticCompositionLocalOf {
    ExtraThemeColors(
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified
    )
}

object AppTheme {
    private val LocalColors = staticCompositionLocalOf { AppThemeColors.light }

    val colors: AppThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (darkTheme) AppThemeColors.dark else AppThemeColors.light
        val extraColors = ExtraThemeColors(
            tertiary = colors.tertiary,
            onTertiary = colors.onTertiary
        )

        CompositionLocalProvider(
            LocalColors provides colors,
            LocalExtraThemeColors provides extraColors
        ) {
            MaterialTheme(
                colors = colors.material
            ) {
                content()
            }
        }
    }
}