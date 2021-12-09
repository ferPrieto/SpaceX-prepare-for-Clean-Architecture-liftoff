package prieto.fernando.spacex.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.BuildConfig
import prieto.fernando.spacex.presentation.EntryPointActivity
import prieto.fernando.spacex.theme.SpaceXTheme
import prieto.fernando.spacex.webmock.SuccessDispatcher


@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenKtTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

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

    @InternalCoroutinesApi
    private fun setMainContent() {
        composeTestRule.setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }
}