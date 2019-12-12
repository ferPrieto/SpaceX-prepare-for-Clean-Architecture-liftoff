package prieto.fernando.spacex.main

import android.os.SystemClock
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.BuildConfig
import prieto.fernando.spacex.utils.TestConfigurationRule
import prieto.fernando.spacex.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @get:Rule
    val espressoRule = TestConfigurationRule()

    private val mockWebServer = MockWebServer()


    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertRecyclerViewIsNotDisplayed()
            assertProgressBarBodyIsDisplayed()
            assertProgressBarHeaderIsNotDisplayed()
        }
    }

    @Test
    fun itemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()
        SystemClock.sleep(2000)
        dashboardFragmentRobot {
            assertRecyclerViewIsDisplayed()
            assertItemsSize()
        }
    }
}