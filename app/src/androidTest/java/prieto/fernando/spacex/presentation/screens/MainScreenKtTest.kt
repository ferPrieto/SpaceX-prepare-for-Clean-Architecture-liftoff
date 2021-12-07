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


@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenKtTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

    private val mockWebServer = MockWebServer()

    @InternalCoroutinesApi
    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start(BuildConfig.PORT)
        composeTestRule.setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }


    @Test
    fun bottom_tabs_displayed() {
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithText("Dashboard", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun app_loads_dashboard_by_default() {
        composeTestRule.onNodeWithText("COMPANY", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun appNavigatesToLaunchesTabWhenSelected() {
        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithText("LAUNCHES", useUnmergedTree = true).assertIsDisplayed()
    }
}