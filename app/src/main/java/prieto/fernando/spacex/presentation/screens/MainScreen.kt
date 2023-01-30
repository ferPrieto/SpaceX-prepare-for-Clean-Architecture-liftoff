package prieto.fernando.spacex.presentation.screens

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import kotlinx.coroutines.InternalCoroutinesApi
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.navigation.BottomNavigationScreens
import prieto.fernando.spacex.presentation.screens.dashboard.DashboardScreen
import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.screens.launches.LaunchesScreen
import prieto.fernando.spacex.presentation.vm.DashboardViewModel
import prieto.fernando.spacex.presentation.vm.LaunchesViewModel
import prieto.fernando.spacex.theme.SpaceX.LocalColors
import prieto.fernando.spacex.theme.SpaceX.LocalTypography
import prieto.fernando.spacex.theme.darkSpaceXColors
import prieto.fernando.spacex.theme.lightSpaceXColors

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        if (MaterialTheme.colors.isLight) lightSpaceXColors.statusBar
        else darkSpaceXColors.statusBar
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
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
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
        sheetBackgroundColor = LocalColors.current.bottomTrayBackground,
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
                navigateTo(
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
                    is LaunchesContract.Effect.ClickableLink.Youtube ->
                        youTubeLinkState.value =
                            effect.youTubeLink
                    is LaunchesContract.Effect.ClickableLink.Wikipedia ->
                        wikipediaLinkState.value =
                            effect.wikipedia
                    else -> {
                        youTubeLinkState.value = ""
                        wikipediaLinkState.value = ""
                    }
                }
            }
        )
    }
}

private fun navigateTo(context: Context, pageUrl: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(pageUrl))
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
                    style = LocalTypography.current.button,
                    color = LocalColors.current.textColorPrimary
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
                    color = LocalColors.current.textColorSecondary,
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
                    style = LocalTypography.current.button,
                    color = LocalColors.current.textColorPrimary
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
        backgroundColor = LocalColors.current.bottomNavBackground
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
    if (selected) LocalColors.current.selectedTab
    else LocalColors.current.unselectedTab

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
        color = animatedColor
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
