package prieto.fernando.feature.dashboard.presentation.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.feature.dashboard.presentation.vm.DashboardContract
import prieto.fernando.shared.ui.theme.SpaceX.SpaceXTheme

/**
 * Isolated component tests for DashboardScreen.
 * Tests the screen composable in isolation without depending on the app module or DI.
 */
@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardScreen_showsLoadingState() {
        // Given - loading state
        val companyInfo = CompanyInfoUiModel(
            name = "",
            founder = "",
            foundedYear = "",
            employees = "",
            launchSites = 0,
            valuation = 0L
        )
        val state = DashboardContract.State(
            companyInfoUiModel = companyInfo,
            isLoading = true,
            isError = false
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                DashboardScreen(state = state)
            }
        }

        // Then - loading animation should be displayed
        composeTestRule.onNodeWithContentDescription("Loading Animation").assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_showsCompanyInfo_whenDataLoaded() {
        // Given - state with company info
        val companyInfo = CompanyInfoUiModel(
            name = "SpaceX",
            founder = "Elon Musk",
            foundedYear = "2002",
            employees = "7000",
            launchSites = 3,
            valuation = 27500000000L
        )
        val state = DashboardContract.State(
            companyInfoUiModel = companyInfo,
            isLoading = false,
            isError = false
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                DashboardScreen(state = state)
            }
        }

        // Then - company information should be displayed
        composeTestRule.onNodeWithText("COMPANY", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("SpaceX", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Elon Musk", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Planet Animation").assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_showsErrorState_whenErrorOccurs() {
        // Given - error state
        val companyInfo = CompanyInfoUiModel(
            name = "",
            founder = "",
            foundedYear = "",
            employees = "",
            launchSites = 0,
            valuation = 0L
        )
        val state = DashboardContract.State(
            companyInfoUiModel = companyInfo,
            isLoading = false,
            isError = true
        )

        // When
        composeTestRule.setContent {
            SpaceXTheme {
                DashboardScreen(state = state)
            }
        }

        // Then - error message should be displayed
        composeTestRule.onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("404 Animation").assertIsDisplayed()
    }
}

