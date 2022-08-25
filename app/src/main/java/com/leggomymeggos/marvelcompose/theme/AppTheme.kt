package com.leggomymeggos.marvelcompose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    private val LocalColors = staticCompositionLocalOf { AppThemeColors.Theme.Default.light }

    enum class Name {
        DEFAULT,
        HULK
    }

    val colors: AppThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        themeName: Name = Name.DEFAULT,
        content: @Composable () -> Unit
    ) {

        val colors = AppThemeColors.getColors(!darkTheme, themeName)
        val extraColors = ExtraThemeColors(
            tertiary = colors.tertiary,
            onTertiary = colors.onTertiary
        )
        val systemUi = rememberSystemUiController()
        // TODO update this for dark mode (to match with surface)
        systemUi.setStatusBarColor(colors.material.primaryVariant)

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