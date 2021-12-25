package prieto.fernando.spacex.presentation.screens.screenshot

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Test
import prieto.fernando.spacex.presentation.screens.base.BaseScreenTest
import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.screens.launches.LaunchesScreen
import prieto.fernando.spacex.presentation.screens.launches.launchesScreenRobot
import prieto.fernando.spacex.theme.SpaceXTheme
import prieto.fernando.spacex.webmock.ErrorDispatcher

@HiltAndroidTest
class LaunchesScreenScreenshotTests : ScreenshotTest, BaseScreenTest() {

    @ExperimentalMaterialApi
    @InternalCoroutinesApi
    @Test
    fun showsEmptyCaseIfThereAreNoSuperHeroes() {
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
            compareScreenshot(this)
        }
    }

    @Test
    @InternalCoroutinesApi
    fun errorTextVisibleWhenConnectionError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        setMainContent()

        compareScreenshot(composeTestRule)
    }
}