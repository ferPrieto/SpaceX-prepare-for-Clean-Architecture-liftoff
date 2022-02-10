package prieto.fernando.spacex.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import prieto.fernando.spacex.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableRes: Int
) {
    object Dashboard : BottomNavigationScreens(
        "Dashboard",
        R.string.tab_title_dashboard,
        R.drawable.ic_dashboard
    )

    object Launches : BottomNavigationScreens(
        "Launches",
        R.string.tab_title_launches,
        R.drawable.ic_launches
    )
}
