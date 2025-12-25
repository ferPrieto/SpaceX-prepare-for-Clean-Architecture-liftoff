package prieto.fernando.feature.navigation

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import prieto.fernando.feature.launches.presentation.LaunchesContract
import prieto.fernando.feature.launches.presentation.LaunchesScreen
import prieto.fernando.feature.launches.presentation.LaunchesViewModel
import prieto.fernando.shared.ui.theme.SpaceX.LocalColors
import prieto.fernando.shared.ui.theme.SpaceX.LocalTypography
import androidx.core.net.toUri


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavDisplay(
    backStack: NavBackStack,
    paddingValues: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val current = backStack.current

    // AnimatedContent provides smooth transitions between destinations
    AnimatedContent(
        targetState = current,
        modifier = modifier,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() with
                    slideOutHorizontally { -it } + fadeOut()
        }
    ) { key ->
        key?.let {
            val content = resolveNavKeyToContent(
                key = it,
                paddingValues = paddingValues,
                onLinkClicked = { link -> navigateTo(context, link) }
            )
            content()
        }
    }
}

private fun navigateTo(context: Context, pageUrl: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, pageUrl.toUri())
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun LaunchesBottomSheetLayout(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    launchesViewModel: LaunchesViewModel,
    paddingValues: PaddingValues,
    youTubeLinkState: MutableState<String>,
    wikipediaLinkState: MutableState<String>,
    onLinkClicked: (String) -> Unit
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
        LaunchesScreen(
            state = launchesViewModel.viewState.value,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            coroutineScope = coroutineScope,
            onEventSent = { event -> launchesViewModel.setEvent(event) },
            effectFlow = launchesViewModel.effect,
            onLinkClicked = { linkClickedEffect ->
                onLinkClicked(linkClickedEffect.link)
            },
            onClickableLinkRetrieved = { effect ->
                when (effect) {
                    is LaunchesContract.Effect.ClickableLink.All -> {
                        youTubeLinkState.value = effect.youTubeLink
                        wikipediaLinkState.value = effect.wikipedia
                    }
                    is LaunchesContract.Effect.ClickableLink.Youtube ->
                        youTubeLinkState.value = effect.youTubeLink
                    is LaunchesContract.Effect.ClickableLink.Wikipedia ->
                        wikipediaLinkState.value = effect.wikipedia
                    else -> {
                        youTubeLinkState.value = ""
                        wikipediaLinkState.value = ""
                    }
                }
            }
        )
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
                    text = stringResource(id = prieto.fernando.shared.ui.R.string.bottom_sheet_youtube),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { onEventSent(LaunchesContract.Event.LinkClicked(youTubeLinkState.value)) },
                    style = LocalTypography.current.button,
                    color = LocalColors.current.textColorPrimary
                )
                Image(
                    painter = painterResource(prieto.fernando.shared.ui.R.drawable.ic_youtube),
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
                    text = stringResource(id = prieto.fernando.shared.ui.R.string.bottom_sheet_wikipedia),
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
                    painter = painterResource(prieto.fernando.shared.ui.R.drawable.ic_wikipedia),
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

