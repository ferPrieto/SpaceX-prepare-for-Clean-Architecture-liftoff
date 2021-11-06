package prieto.fernando.spacex.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import prieto.fernando.spacex.ui.dashboard.DashboardScreen
import prieto.fernando.spacex.ui.navigation.BottomNavigationScreens
import prieto.fernando.spacex.ui.vm.DashboardViewModel
import prieto.fernando.spacex.ui.vm.DashboardViewModelImpl

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Dashboard,
        BottomNavigationScreens.Launches
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, bottomNavigationItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Dashboard.route) {
        composable(BottomNavigationScreens.Dashboard.route) {
            val dashboardViewModel = hiltViewModel<DashboardViewModelImpl>()

            DashboardScreen(viewModel = dashboardViewModel)
        }
        composable(BottomNavigationScreens.Launches.route) {
            // LaunchesScreen( )
        }
    }
}

@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(screen.drawableRes),
                        stringResource(id = screen.resourceId)
                    )
                },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}