package prieto.fernando.spacex.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.presentation.EntryPointActivity
import prieto.fernando.spacex.theme.SpaceXTheme


@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
class MainScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

    @InternalCoroutinesApi
    @Before
    fun setUp() {
        composeTestRule.setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
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
}