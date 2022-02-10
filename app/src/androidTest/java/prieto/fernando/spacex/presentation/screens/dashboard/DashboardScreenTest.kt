package prieto.fernando.spacex.presentation.screens.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.presentation.screens.base.BaseScreenTest
import prieto.fernando.spacex.webmock.ErrorDispatcher
import prieto.fernando.spacex.webmock.SuccessDispatcher

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DashboardScreenTest : BaseScreenTest() {

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.apply {
            onNodeWithText("COMPANY", useUnmergedTree = true).assertIsDisplayed()
            onNodeWithText("was founded by", substring = true).assertIsDisplayed()
            onNodeWithContentDescription("Planet Animation").assertIsDisplayed()
        }
    }

    @Test
    @InternalCoroutinesApi
    fun errorTextVisibleWhenConnectionError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        setMainContent()

        composeTestRule.apply {
            onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true).assertIsDisplayed()
            onNodeWithContentDescription("404 Animation").assertIsDisplayed()
        }
    }
}
