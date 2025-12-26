package prieto.fernando.shared.testing.android.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

/**
 * Base class for instrumentation tests that require Hilt DI and MockWebServer.
 * 
 * Usage:
 * 1. Extend this class in your feature module's androidTest
 * 2. Define your own BuildConfig.PORT in your module
 * 3. Override composeTestRule with your specific AndroidComposeTestRule
 * 4. Call setMainContent() or define your own content setup
 */
abstract class BaseScreenTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    /**
     * Override this in your test class to provide the ComposeTestRule.
     * Example:
     * override val composeTestRule = createAndroidComposeRule<EntryPointActivity>()
     */
    abstract val composeTestRule: ComposeContentTestRule

    val mockWebServer by lazy { MockWebServer() }

    /**
     * Override this in your feature module to provide the mock server port.
     */
    abstract fun getMockServerPort(): Int

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start(getMockServerPort())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}

