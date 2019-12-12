package prieto.fernando.spacex.main

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
class MainFragmentTest {

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
    fun elementsVisible() {
        mockWebServer.dispatcher = SuccessDispatcher()

        infiniteFragmentRobot {
           /* assertRecyclerViewDisplayed()
            assertProgressBarBodyDisplayed()
            assertProgressBarHeaderDisplayed()*/
            assertBodyDisplayed()
        }
    }

    @Test
    fun itemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        infiniteFragmentRobot {
            assertItemsSize()
        }
    }
}