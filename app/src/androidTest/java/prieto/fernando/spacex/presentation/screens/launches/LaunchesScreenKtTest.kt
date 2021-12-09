package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.BuildConfig
import prieto.fernando.spacex.presentation.EntryPointActivity
import prieto.fernando.spacex.presentation.screens.MainScreen
import prieto.fernando.spacex.theme.SpaceXTheme
import prieto.fernando.spacex.webmock.ErrorDispatcher
import prieto.fernando.spacex.webmock.SuccessDispatcher

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LaunchesScreenKtTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val composeTestRule by lazy { createAndroidComposeRule<EntryPointActivity>() }

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Launches").performClick()
        composeTestRule.onNodeWithContentDescription("Launches Animation", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("LAUNCHES").assertIsDisplayed()
    }

    @Test
    @InternalCoroutinesApi
    fun visibleItemsCountAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Launches").performClick()
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onAllNodesWithContentDescription(
            "Item",
            substring = true,
            useUnmergedTree = true
        ).assertCountEquals(6)
    }

    @Test
    @InternalCoroutinesApi
    fun errorTextVisibleWhenConnectionError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("404 Animation").assertIsDisplayed()
    }

    @Test
    @InternalCoroutinesApi
    fun dialogVisibleAfterClickingOnFilter() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Launches").performClick()
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithContentDescription("Filter Button").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("FILTER BY YEAR", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    @InternalCoroutinesApi
    fun noItemsRetrieved() {
        composeTestRule.setContent {
            SpaceXTheme {
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchesScreen(
                    state = LaunchesContract.State(emptyList(), isLoading = false, isError = false),
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    onEventSent = {},
                    effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                    onLinkClicked = { },
                    onClickableLinkRetrieved = { })
            }
        }
        composeTestRule.onNodeWithText("NO RESULTS FOUND", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(
            "Item",
            substring = true,
            useUnmergedTree = true
        ).assertCountEquals(0)
    }

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterTwoItemsRetrieved() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithText("Mission1", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Mission2", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(
            "Item",
            substring = true,
            useUnmergedTree = true
        ).assertCountEquals(2)
    }

    @InternalCoroutinesApi
    private fun setMainContent() {
        composeTestRule.setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }
}