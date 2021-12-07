package prieto.fernando.spacex.presentation.screens.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.presentation.EntryPointActivity
import prieto.fernando.spacex.presentation.screens.MainScreen
import prieto.fernando.spacex.theme.SpaceXTheme

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest  {
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
    fun clickBottomNavigationItemToLaunchesScreen() {
        composeTestRule.onNodeWithText("Launches", useUnmergedTree = true).performClick().assertIsDisplayed()
        composeTestRule.onNodeWithText("LAUNCHES", useUnmergedTree = true).assertIsDisplayed()
    }
}