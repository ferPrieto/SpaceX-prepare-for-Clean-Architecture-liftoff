package prieto.fernando.feature.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.InternalCoroutinesApi
import prieto.fernando.shared.ui.theme.SpaceX.LocalColors
import prieto.fernando.shared.ui.theme.darkSpaceXColors
import prieto.fernando.shared.ui.theme.lightSpaceXColors

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Preview
@Composable
fun MainScreen() {
    // Navigation 3: Create and remember the back stack
    val navBackStack = rememberNavBackStack()

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
            BottomNavigation(
                backStack = navBackStack,
                items = bottomNavigationItems
            )
        }
    ) { innerPadding ->
        // Navigation 3: Use NavDisplay to observe and display the back stack
        NavDisplay(
            backStack = navBackStack,
            paddingValues = innerPadding
        )
    }
}

@Composable
private fun BottomNavigation(
    backStack: NavBackStack,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation(
        backgroundColor = LocalColors.current.bottomNavBackground
    ) {
        val currentKey = backStack.current
        items.forEach { screen ->
            BottomTab(
                screen = screen,
                isSelected = currentKey == screen.toNavKey(),
                onClick = {
                    // Navigation 3: Directly manipulate the back stack
                    val targetKey = screen.toNavKey()
                    if (currentKey != targetKey) {
                        // Pop to root and push new destination
                        backStack.popTo(NavKey.Dashboard, inclusive = false)
                        if (targetKey != NavKey.Dashboard) {
                            backStack.push(targetKey)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.BottomTab(
    screen: BottomNavigationScreens,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        icon = {
            AnimatableIcon(
                modifier = Modifier,
                screen = screen,
                scale = if (isSelected) 1.2f else 1f,
                color = getTabColour(isSelected),
                onClick = onClick
            )
        },
        label = {
            AnimatableText(
                screen = screen,
                color = getTabColour(isSelected)
            )
        },
        selected = isSelected,
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

// Helper to convert BottomNavigationScreens to NavKey
private fun BottomNavigationScreens.toNavKey(): NavKey = when (this) {
    BottomNavigationScreens.Dashboard -> NavKey.Dashboard
    BottomNavigationScreens.Launches -> NavKey.Launches
}

