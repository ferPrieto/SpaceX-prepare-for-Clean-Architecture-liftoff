package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.presentation.screens.BaseScreenTest
import prieto.fernando.spacex.theme.SpaceXTheme
import prieto.fernando.spacex.webmock.ErrorDispatcher
import prieto.fernando.spacex.webmock.SuccessDispatcher

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LaunchesScreenKtTest : BaseScreenTest() {

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.apply {
            onNodeWithText("Launches").performClick()
            onNodeWithContentDescription(
                "Launches Animation",
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithText("LAUNCHES").assertIsDisplayed()
        }
    }

    @Test
    @InternalCoroutinesApi
    fun visibleItemsCountAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.apply {
            onNodeWithText("Launches").performClick()
            mainClock.advanceTimeBy(2000)
            onAllNodesWithContentDescription(
                "Item",
                substring = true,
                useUnmergedTree = true
            ).assertCountEquals(6)
        }
    }

    @Test
    @InternalCoroutinesApi
    fun errorTextVisibleWhenConnectionError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        setMainContent()
        composeTestRule.apply {
            onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
                .performClick()
            onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithContentDescription("404 Animation").assertIsDisplayed()
        }

    }

    @Test
    @InternalCoroutinesApi
    fun dialogVisibleAfterClickingOnFilter() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.apply {
            onNodeWithText("Launches").performClick()
            mainClock.advanceTimeBy(2000)
            onNodeWithContentDescription("Filter Button").assertIsDisplayed()
                .performClick()
            onNodeWithText("FILTER BY YEAR", useUnmergedTree = true).assertIsDisplayed()
        }

    }

    @Test
    @InternalCoroutinesApi
    fun noItemsRetrieved() {
        composeTestRule.apply {
            setContent {
                SpaceXTheme {
                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                    )
                    val coroutineScope = rememberCoroutineScope()

                    LaunchesScreen(
                        state = LaunchesContract.State(
                            emptyList(),
                            isLoading = false,
                            isError = false
                        ),
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        coroutineScope = coroutineScope,
                        onEventSent = {},
                        effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                        onLinkClicked = { },
                        onClickableLinkRetrieved = { })
                }
            }
            onNodeWithText("NO RESULTS FOUND", useUnmergedTree = true)
                .assertIsDisplayed()
            onAllNodesWithContentDescription(
                "Item",
                substring = true,
                useUnmergedTree = true
            ).assertCountEquals(0)
        }
    }

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterTwoItemsRetrieved() {
        composeTestRule.apply {
            setContent {
                SpaceXTheme {
                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                    )
                    val coroutineScope = rememberCoroutineScope()

                    LaunchesScreen(
                        state = LaunchesContract.State(
                            listOf(
                                LaunchUiModel(
                                    "Mission1", "08-12-2021", true, "0", RocketUiModel(
                                        "Rocket1", "Rocket Type1"
                                    ), LinksUiModel("", "", "Youtube Link"), true
                                ),
                                LaunchUiModel(
                                    "Mission2", "09-12-2021", false, "0", RocketUiModel(
                                        "Rocket2", "Rocket Type2"
                                    ), LinksUiModel("", "WikiPedia Link", "Youtube Link"), false
                                )
                            ), false, false
                        ),
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        coroutineScope = coroutineScope,
                        onEventSent = {},
                        effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                        onLinkClicked = { },
                        onClickableLinkRetrieved = { })
                }
            }

            onNodeWithText("Mission1", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Mission2", useUnmergedTree = true)
                .assertIsDisplayed()
            onAllNodesWithContentDescription(
                "Item",
                substring = true,
                useUnmergedTree = true
            ).assertCountEquals(2)
        }
    }
}