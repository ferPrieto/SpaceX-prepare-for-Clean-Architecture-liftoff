package prieto.fernando.spacex.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import prieto.fernando.spacex.presentation.dashboard.DashboardScreen
import prieto.fernando.spacex.presentation.launches.LaunchesScreen
import prieto.fernando.spacex.presentation.navigation.BottomNavigationScreens
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.theme.SpaceXTypography
import prieto.fernando.spacex.presentation.vm.DashboardViewModelImpl
import prieto.fernando.spacex.presentation.vm.LaunchesViewModelImpl

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        if (MaterialTheme.colors.isLight) Light.StatusBar
        else Dark.StatusBar
    )

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Dashboard,
        BottomNavigationScreens.Launches
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, bottomNavigationItems, Modifier)
        }
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
            InitDashboardScreen()
        }
        composable(BottomNavigationScreens.Launches.route) {
            InitLaunchesScreen()
        }
    }
}

@Composable
private fun InitDashboardScreen() {
    val dashboardViewModel: DashboardViewModelImpl = hiltViewModel()
    DashboardScreen(state = dashboardViewModel.viewState.value)
}

@Composable
private fun InitLaunchesScreen() {
    val launchesViewModel: LaunchesViewModelImpl = hiltViewModel()
    LaunchesScreen(state = launchesViewModel.viewState.value)
}

@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = if (MaterialTheme.colors.isLight) Light.BottomNavBackground
        else Dark.BottomNavBackground
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomTab(modifier, screen, currentRoute, navController)
        }
    }
}

@Composable
private fun RowScope.BottomTab(
    modifier: Modifier,
    screen: BottomNavigationScreens,
    currentRoute: String?,
    navController: NavHostController
) {
    val onClick: () -> Unit = {
        if (currentRoute != screen.route) {
            navController.navigate(screen.route)
        }
    }
    BottomNavigationItem(
        icon = {
            AnimatableIcon(
                modifier = modifier,
                screen = screen,
                scale = if (currentRoute == screen.route) 1.2f else 1f,
                color = getTabColour(currentRoute == screen.route),
                onClick = onClick
            )
        },
        label = {
            AnimatableText(
                screen = screen,
                color = getTabColour(currentRoute == screen.route)
            )
        },
        selected = currentRoute == screen.route,
        onClick = onClick
    )
}

@Composable
private fun getTabColour(selected: Boolean) =
    if (selected) {
        if (MaterialTheme.colors.isLight) Light.SelectedTab
        else Dark.SelectedTab
    } else {
        if (MaterialTheme.colors.isLight) Light.UnselectedTab
        else Dark.UnselectedTab
    }

@Composable
private fun AnimatableIcon(
    modifier: Modifier,
    screen: BottomNavigationScreens,
    iconSize: Dp = 24.dp,
    scale: Float = 1f,
    color: Color,
    onClick: () -> Unit
) {
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )
    IconButton(
        modifier = modifier.size(iconSize),
        onClick = onClick
    ) {
        Icon(
            painterResource(screen.drawableRes),
            stringResource(id = screen.resourceId),
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}

@Composable
private fun AnimatableText(
    screen: BottomNavigationScreens,
    color: Color
) {
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )
    Text(
        text = stringResource(id = screen.resourceId),
        modifier = Modifier,
        color = animatedColor
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}