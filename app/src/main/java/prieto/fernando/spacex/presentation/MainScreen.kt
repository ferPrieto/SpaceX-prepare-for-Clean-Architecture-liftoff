package prieto.fernando.spacex.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.dashboard.DashboardScreen
import prieto.fernando.spacex.presentation.launches.*
import prieto.fernando.spacex.presentation.navigation.BottomNavigationScreens
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.theme.SpaceXTypography
import prieto.fernando.spacex.presentation.util.UrlUtils
import prieto.fernando.spacex.presentation.vm.DashboardViewModelImpl
import prieto.fernando.spacex.presentation.vm.LaunchesViewModel

@InternalCoroutinesApi
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

@InternalCoroutinesApi
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

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
private fun InitLaunchesScreen(paddingValues: PaddingValues) {
    val launchesViewModel: LaunchesViewModel = hiltViewModel()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val youTubeLinkState = remember { mutableStateOf("") }
    val wikipediaLinkState = remember { mutableStateOf("") }

    BottomSheet(
        bottomSheetScaffoldState,
        coroutineScope,
        launchesViewModel,
        paddingValues,
        youTubeLinkState,
        wikipediaLinkState
    )
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
private fun BottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    launchesViewModel: LaunchesViewModel,
    paddingValues: PaddingValues,
    youTubeLinkState: MutableState<String>,
    wikipediaLinkState: MutableState<String>
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = if (MaterialTheme.colors.isLight) Light.BottomTrayBackground else Dark.BottomTrayBackground,
        sheetContent = {
            BottomSheetContent(
                youTubeLinkState = youTubeLinkState,
                wikipediaLinkState = wikipediaLinkState,
                onEventSent = { event -> launchesViewModel.setEvent(event) }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        modifier = Modifier
            .padding(paddingValues)
            .wrapContentHeight()
    ) {
        val context = LocalContext.current

        LaunchesScreen(
            state = launchesViewModel.viewState.value,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            coroutineScope = coroutineScope,
            onEventSent = { event -> launchesViewModel.setEvent(event) },
            effectFlow = launchesViewModel.effect,
            onLinkClicked = { linkClickedEffect ->
                UrlUtils.navigateTo(
                    context = context,
                    pageUrl = linkClickedEffect.link
                )
            },
            onClickableLinkRetrieved = { effect ->
                when (effect) {
                    is LaunchesContract.Effect.ClickableLink.All -> {
                        youTubeLinkState.value = effect.youTubeLink
                        wikipediaLinkState.value = effect.wikipedia
                    }
                    is LaunchesContract.Effect.ClickableLink.Youtube -> youTubeLinkState.value =
                        effect.youTubeLink
                    is LaunchesContract.Effect.ClickableLink.Wikipedia -> wikipediaLinkState.value =
                        effect.wikipedia
                    else -> {
                        youTubeLinkState.value = ""
                        wikipediaLinkState.value = ""
                    }
                }
            })
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    youTubeLinkState: MutableState<String>,
    wikipediaLinkState: MutableState<String>,
    onEventSent: (event: LaunchesContract.Event) -> Unit
) {
    Box {
        Row(
            modifier
                .align(Alignment.Center)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            if (youTubeLinkState.value.isNotBlank()) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_youtube),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { onEventSent(LaunchesContract.Event.LinkClicked(youTubeLinkState.value)) },
                    style = SpaceXTypography.button,
                    color = if (MaterialTheme.colors.isLight) Light.TextColorPrimary
                    else Dark.TextColorPrimary
                )
                Image(
                    painter = painterResource(R.drawable.ic_youtube),
                    contentDescription = "YouTube Icon",
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .clickable { onEventSent(LaunchesContract.Event.LinkClicked(youTubeLinkState.value)) }
                )
            }
            if (youTubeLinkState.value.isNotBlank() && wikipediaLinkState.value.isNotBlank()) {
                Divider(
                    color = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary,
                    modifier = modifier
                        .padding(end = 16.dp, start = 16.dp)
                        .width(1.dp)
                        .height(24.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            if (wikipediaLinkState.value.isNotBlank()) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_wikipedia),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onEventSent(
                                LaunchesContract.Event.LinkClicked(
                                    wikipediaLinkState.value
                                )
                            )
                        },
                    style = SpaceXTypography.button,
                    color = if (MaterialTheme.colors.isLight) Light.TextColorPrimary
                    else Dark.TextColorPrimary
                )
                Image(
                    painter = painterResource(R.drawable.ic_wikipedia),
                    contentDescription = "Wikipedia Icon",
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onEventSent(
                                LaunchesContract.Event.LinkClicked(
                                    wikipediaLinkState.value
                                )
                            )
                        }
                )
            }
        }
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