package prieto.fernando.spacex.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.presentation.screens.base.BaseScreenTest
import prieto.fernando.spacex.webmock.SuccessDispatcher

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenKtTest : BaseScreenTest() {

    @InternalCoroutinesApi
    @Test
    fun bottom_tabs_displayed() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Dashboard", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun app_loads_dashboard_by_default() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("COMPANY", useUnmergedTree = true).assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun appNavigatesToLaunchesTabWhenSelected() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithText("LAUNCHES", useUnmergedTree = true).assertIsDisplayed()
    }
}
