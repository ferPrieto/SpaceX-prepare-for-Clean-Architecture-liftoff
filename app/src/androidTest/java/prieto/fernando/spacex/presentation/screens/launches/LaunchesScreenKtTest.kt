package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.BuildConfig
import prieto.fernando.spacex.presentation.EntryPointActivity
import prieto.fernando.spacex.presentation.screens.MainScreen
import prieto.fernando.spacex.theme.SpaceXTheme
import prieto.fernando.spacex.webmock.ErrorDispatcher
import prieto.fernando.spacex.webmock.SuccessDispatcher

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LaunchesScreenKtTest{
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val composeTestRule by lazy { createAndroidComposeRule<EntryPointActivity>() }

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

    @Test
    @InternalCoroutinesApi
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("COMPANY", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("was founded by", substring = true)
            .assertIsDisplayed()
    }

    @Test
    @InternalCoroutinesApi
    fun errorTextVisibleWhenConnectionError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        setMainContent()

        composeTestRule.onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true)
            .assertIsDisplayed()
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