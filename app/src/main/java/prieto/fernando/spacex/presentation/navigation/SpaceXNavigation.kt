package prieto.fernando.spacex.presentation.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object SpaceXDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val LAUNCHES_ROUTE = "launches"
}

class SpaceXNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(SpaceXDestinations.DASHBOARD_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToLaunches: () -> Unit = {
        navController.navigate(SpaceXDestinations.LAUNCHES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }
}
