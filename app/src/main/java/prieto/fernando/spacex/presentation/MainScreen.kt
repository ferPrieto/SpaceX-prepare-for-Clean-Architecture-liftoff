package prieto.fernando.spacex.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import prieto.fernando.spacex.presentation.dashboard.DashboardScreen
import prieto.fernando.spacex.presentation.launches.LaunchesScreen
import prieto.fernando.spacex.presentation.navigation.BottomNavigationScreens
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.vm.DashboardViewModelImpl
import prieto.fernando.spacex.presentation.vm.LaunchesViewModelImpl

@ExperimentalMaterialApi
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
    ) { innerPadding ->
        MainScreenNavigationConfigurations(navController, innerPadding)
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Dashboard.route) {
        composable(BottomNavigationScreens.Dashboard.route) {
            InitDashboardScreen()
        }
        composable(BottomNavigationScreens.Launches.route) {
            InitLaunchesScreen(paddingValues)
        }
    }
}

@Composable
private fun InitDashboardScreen() {
    val dashboardViewModel: DashboardViewModelImpl = hiltViewModel()
    DashboardScreen(state = dashboardViewModel.viewState.value)
}

@ExperimentalMaterialApi
@Composable
private fun InitLaunchesScreen(paddingValues: PaddingValues) {
    val launchesViewModel: LaunchesViewModelImpl = hiltViewModel()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheet(bottomSheetScaffoldState, launchesViewModel, coroutineScope,paddingValues)
}

@ExperimentalMaterialApi
@Composable
private fun BottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    launchesViewModel: LaunchesViewModelImpl,
    coroutineScope: CoroutineScope,
    paddingValues: PaddingValues
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = if (MaterialTheme.colors.isLight) Light.BottomTrayBackground else Dark.BottomTrayBackground,
        sheetContent = {
            Box(
                Modifier
                    .padding(top = 32.dp, bottom = 32.dp)
            ) {
                Row {
                    Text(text = "YouTube")
                    Text(text = "Wikipedia")
                }
            }
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        modifier = Modifier
            .padding(paddingValues)
            .wrapContentHeight()
    ) {
        LaunchesScreen(
            state = launchesViewModel.viewState.value,
            coroutineScope = coroutineScope,
            bottomSheetScaffoldState = bottomSheetScaffoldState
        )
    }
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