package ferprieto.spacex.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import ferprieto.feature.navigation.MainScreen
import ferprieto.shared.testing.android.base.BaseScreenTest
import ferprieto.shared.testing.android.webmock.SuccessDispatcher
import ferprieto.shared.ui.theme.SpaceX.SpaceXTheme
import ferprieto.spacex.BuildConfig
import ferprieto.spacex.EntryPointActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenKtTest : BaseScreenTest() {
    @get:Rule
    override val composeTestRule: ComposeContentTestRule = createAndroidComposeRule<EntryPointActivity>()

    override fun getMockServerPort(): Int = BuildConfig.PORT

    @ExperimentalMaterialApi
    @InternalCoroutinesApi
    private fun setMainContent() {
        composeTestRule.setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }

    @InternalCoroutinesApi
    @Test
    fun bottom_tabs_displayed() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("Dashboard", useUnmergedTree = true).assertIsDisplayed()
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
