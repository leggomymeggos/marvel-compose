package com.leggomymeggos.marvelcompose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

data class AppThemeColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val isLight: Boolean
) {
    companion object {
        fun getColors(isLight: Boolean, themeName: AppTheme.Name): AppThemeColors {
            val theme = when (themeName) {
                AppTheme.Name.DEFAULT -> Theme.Default
                AppTheme.Name.HULK -> Theme.Hulk
            }
            return if (isLight) theme.light else theme.dark
        }
    }

    val material by lazy {
        if (isLight) {
            lightColors(
                primary = this.primary,
                onPrimary = this.onPrimary,
                primaryVariant = this.primary,
                secondary = this.secondary,
                secondaryVariant = this.secondary,
                onSecondary = this.onSecondary,
                background = this.background,
                onBackground = this.onBackground,
                surface = this.surface,
                onSurface = this.onSurface,
                // TODO other colors (error, primary/secondary variant, etc)
            )
        } else {
            darkColors(
                primary = this.primary,
                primaryVariant = this.primary,
                onPrimary = this.onPrimary,
                secondary = this.secondary,
                secondaryVariant = this.secondary,
                onSecondary = this.onSecondary,
                background = this.background,
                onBackground = this.onBackground,
                surface = this.surface,
                onSurface = this.onSurface,
                // TODO other colors (error, primary/secondary variant, etc)
            )
        }
    }

    sealed class Theme {
        abstract val light: AppThemeColors
        abstract val dark: AppThemeColors

        object Default: Theme() {
            override val light = AppThemeColors(
                primary = BaseColors.Red,
                onPrimary = BaseColors.White,
                secondary = BaseColors.Orange,
                onSecondary = BaseColors.Black,
                tertiary = BaseColors.BlueDark,
                onTertiary = BaseColors.Black,
                background = BaseColors.Gray,
                onBackground = BaseColors.Black,
                surface = BaseColors.GrayLight,
                onSurface = BaseColors.Black,
                // TODO other colors (error, primary/secondary variant, etc)
                isLight = true
            )

            override val dark = AppThemeColors(
                primary = BaseColors.RedLight,
                onPrimary = BaseColors.White,
                secondary = BaseColors.OrangeLight,
                onSecondary = BaseColors.Black,
                tertiary = BaseColors.BlueLight,
                onTertiary = BaseColors.Black,
                background = BaseColors.DarkGray,
                onBackground = BaseColors.White,
                surface = BaseColors.DarkGrayLight,
                onSurface = BaseColors.White,
                // TODO other colors (error, primary/secondary variant, etc)
                isLight = false
            )

        }

        object Hulk: Theme() {
            override val light = Default.light.copy(
                primary = BaseColors.Purple,
                onPrimary = BaseColors.White,
                secondary = BaseColors.Green,
                onSecondary = BaseColors.Black,
                tertiary = BaseColors.GreenDark,
                onTertiary = BaseColors.Black,
                // TODO other colors (error, primary/secondary variant, etc)
            )

            override val dark = Default.dark.copy(
                primary = BaseColors.PurpleLight,
                onPrimary = BaseColors.Black,
                secondary = BaseColors.GreenLight,
                onSecondary = BaseColors.Black,
                tertiary = BaseColors.Green,
                onTertiary = BaseColors.Black,
                // TODO other colors (error, primary/secondary variant, etc)
            )
        }
    }
}
