package prieto.fernando.spacex.theme

import android.graphics.Color.parseColor
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SpaceXColors(
    val primary: Color,
    val primaryDark: Color,
    val accent: Color,
    val accentSecondary: Color,
    val textColorPrimary: Color,
    val textColorSecondary: Color,
    val textHighlighted: Color,
    val background: Color,
    val itemBackground: Color,
    val dialogWindowBackground: Color,
    val bottomNavBackground: Color,
    val bottomTrayBackground: Color,
    val statusBar: Color,
    val selectedTab: Color,
    val unselectedTab: Color
)

val lightSpaceXColors: SpaceXColors = SpaceXColors(
    primary = Color(parseColor("#00A2EA")),
    primaryDark = Color(parseColor("#303F9F")),
    accent = Color(parseColor("#FF4081")),
    accentSecondary = Color(parseColor("#2C8A45")),
    textColorPrimary = Color(parseColor("#000000")),
    textColorSecondary = Color(parseColor("#7F7F82")),
    textHighlighted = Color(parseColor("#58B5E5")),
    background = Color(parseColor("#E3E6E9")),
    itemBackground = Color(parseColor("#FFFFFF")),
    dialogWindowBackground = Color(parseColor("#E0E5E9")),
    bottomNavBackground = Color(parseColor("#BBC3C8")),
    bottomTrayBackground = Color(parseColor("#F7F7F7")),
    statusBar = Color(parseColor("#ADB4B9")),
    selectedTab = Color(parseColor("#00A2EA")),
    unselectedTab = Color(parseColor("#7F7F82")),
)

val darkSpaceXColors: SpaceXColors = SpaceXColors(
    primary = Color(0xff3f51b5),
    primaryDark = Color(0xff303f9f),
    accent = Color(0xffff4081),
    accentSecondary = Color(0xff54b06d),
    textColorPrimary = Color(0xffffffff),
    textColorSecondary = Color(0xff7f7f82),
    textHighlighted = Color(0xff58b5e5),
    background = Color(0xff2b2c35),
    itemBackground = Color(0xff202026),
    dialogWindowBackground = Color(0xff393a47),
    bottomNavBackground = Color(0xff393A47),
    bottomTrayBackground = Color(0xff4C4E5D),
    statusBar = Color(0xff212027),
    selectedTab = Color(0xff3F51B5),
    unselectedTab = Color(0xff7f7f82)
)
