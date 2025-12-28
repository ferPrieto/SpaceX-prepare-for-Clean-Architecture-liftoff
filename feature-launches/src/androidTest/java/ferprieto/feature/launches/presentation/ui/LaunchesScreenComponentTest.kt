@file:OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.InternalCoroutinesApi::class)

package ferprieto.feature.launches.presentation.ui

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ferprieto.feature.launches.presentation.vm.LaunchesContract
import ferprieto.shared.ui.theme.SpaceX.SpaceXTheme

/**
 * Isolated component tests for LaunchesScreen.
 * Tests the screen composable in isolation without depending on the app module or DI.
 */
@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
class LaunchesScreenComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun launchesScreen_showsLoadingState() {
        // Given - loading state
        val state = LaunchesContract.State(
            launchUiModels = emptyList(),
            isLoading = true,
            isError = false
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                val density = LocalDensity.current
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(
                        initialValue = BottomSheetValue.Collapsed,
                        density = density
                    )
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchesScreen(
                    state = state,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    onEventSent = {},
                    effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                    onLinkClicked = {},
                    onClickableLinkRetrieved = {}
                )
            }
        }

        // Then - loading animation should be displayed
        composeTestRule.onNodeWithContentDescription("Loading Animation").assertIsDisplayed()
    }

    @Test
    fun launchesScreen_showsEmptyState_whenNoLaunches() {
        // Given - empty state
        val state = LaunchesContract.State(
            launchUiModels = emptyList(),
            isLoading = false,
            isError = false
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                val density = LocalDensity.current
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(
                        initialValue = BottomSheetValue.Collapsed,
                        density = density
                    )
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchesScreen(
                    state = state,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    onEventSent = {},
                    effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                    onLinkClicked = {},
                    onClickableLinkRetrieved = {}
                )
            }
        }

        // Then - empty state should be displayed
        composeTestRule.onNodeWithText("NO RESULTS FOUND", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun launchesScreen_showsLaunchItems_whenDataLoaded() {
        // Given - state with launch data
        val launchUiModels = listOf(
            LaunchUiModel(
                missionName = "Falcon 9",
                launchDate = "12-01-2021",
                launchSuccess = true,
                differenceOfDays = "0",
                rocketUiModel = RocketUiModel("Falcon 9", "v1.0"),
                linksUiModel = LinksUiModel("", "", ""),
                isPastLaunch = true
            ),
            LaunchUiModel(
                missionName = "Starship",
                launchDate = "15-02-2021",
                launchSuccess = false,
                differenceOfDays = "5",
                rocketUiModel = RocketUiModel("Starship", "Prototype"),
                linksUiModel = LinksUiModel("", "", ""),
                isPastLaunch = false
            )
        )
        val state = LaunchesContract.State(
            launchUiModels = launchUiModels,
            isLoading = false,
            isError = false
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                val density = LocalDensity.current
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(
                        initialValue = BottomSheetValue.Collapsed,
                        density = density
                    )
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchesScreen(
                    state = state,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    onEventSent = {},
                    effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                    onLinkClicked = {},
                    onClickableLinkRetrieved = {}
                )
            }
        }

        // Then - launch items should be displayed
        composeTestRule.onNodeWithText("LAUNCHES", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Falcon 9", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Starship", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("Item", substring = true, useUnmergedTree = true)
            .assertCountEquals(2)
    }

    @Test
    fun launchesScreen_showsErrorState_whenErrorOccurs() {
        // Given - error state
        val state = LaunchesContract.State(
            launchUiModels = emptyList(),
            isLoading = false,
            isError = true
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                val density = LocalDensity.current
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(
                        initialValue = BottomSheetValue.Collapsed,
                        density = density
                    )
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchesScreen(
                    state = state,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    onEventSent = {},
                    effectFlow = flow { emit(LaunchesContract.Effect.ClickableLink.None) },
                    onLinkClicked = {},
                    onClickableLinkRetrieved = {}
                )
            }
        }

        // Then - error message should be displayed
        composeTestRule.onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("404 Animation").assertIsDisplayed()
    }
}

