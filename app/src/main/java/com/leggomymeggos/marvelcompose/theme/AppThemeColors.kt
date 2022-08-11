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
        val light = AppThemeColors(
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

        val dark = AppThemeColors(
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

    val material by lazy {
        if (isLight) {
            lightColors(
                primary = light.primary,
                onPrimary = light.onPrimary,
                secondary = light.secondary,
                onSecondary = light.onSecondary,
                background = light.background,
                onBackground = light.onBackground,
                surface = light.surface,
                onSurface = light.onSurface,

                primaryVariant = BaseColors.RedDark,
                // TODO other colors (error, primary/secondary variant, etc)
            )
        } else {
            darkColors(
                primary = dark.primary,
                onPrimary = dark.onPrimary,
                secondary = dark.secondary,
                onSecondary = dark.onSecondary,
                background = dark.background,
                onBackground = dark.onBackground,
                surface = dark.surface,
                onSurface = dark.onSurface,

                primaryVariant = BaseColors.Red
                // TODO other colors (error, primary/secondary variant, etc)
            )
        }
    }
}
