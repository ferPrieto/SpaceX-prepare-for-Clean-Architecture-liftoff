package prieto.fernando.spacex.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

object SpaceX {
    val LocalColors = staticCompositionLocalOf { lightSpaceXColors }
    val LocalTypography = staticCompositionLocalOf { SpaceXTypography(lightSpaceXColors) }

    @Composable
    fun SpaceXTheme(
        isDarkMode: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (isDarkMode) darkSpaceXColors else lightSpaceXColors
        val typography = SpaceXTypography(colors)

        CompositionLocalProvider(
            LocalColors provides colors,
            LocalTypography provides typography,
            content = content
        )
    }
}
