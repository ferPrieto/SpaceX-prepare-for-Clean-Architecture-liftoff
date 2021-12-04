package prieto.fernando.spacex.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Light.Primary,
    primaryVariant = Light.PrimaryDark,
    onPrimary = Color.White,
    secondary = Light.Accent,
    secondaryVariant = Light.AccentSecondary,
    onSecondary = Color.White,
    error = Light.TextHighlighted,
    onBackground = Light.Background
)

private val DarkThemeColors = darkColors(
    primary = Dark.Primary,
    primaryVariant = Dark.PrimaryDark,
    onPrimary = Color.Black,
    secondary = Dark.Accent,
    secondaryVariant = Dark.AccentSecondary,
    onSecondary = Color.Black,
    error = Dark.TextHighlighted,
    onBackground = Dark.Background
)

@Composable
fun SpaceXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = typography,
        shapes = SpaceXShapes,
        content = content
    )
}
