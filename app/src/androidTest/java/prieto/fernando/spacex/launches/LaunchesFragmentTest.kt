package prieto.fernando.spacex.launches

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.spacex.BuildConfig
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.MainActivity
import prieto.fernando.spacex.utils.TestConfigurationRule
import prieto.fernando.spacex.utils.ViewVisibilityIdlingResource
import prieto.fernando.spacex.webmock.ErrorDispatcher
import prieto.fernando.spacex.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class LaunchesFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @get:Rule
    val espressoRule = TestConfigurationRule()

    private val mockWebServer = MockWebServer()

    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertRecyclerViewIsNotDisplayed()
            assertProgressBarBodyIsDisplayed()
            assertProgressBarHeaderIsNotDisplayed()
            assertToolbarIsDisplayed()
            assertFilterButtonIsDisplayed()
        }
    }

    @Test
    fun itemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            assertItemsSize()
        }
    }

    @Test
    fun clickItemShowBottomSheet() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickItem(3)
            youtubeIconViewMatcher()
        }
    }

    @Test
    fun clickItemAndShowDialog() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickFilter()
            dialogYearViewMatcher()
        }
    }

    @Test
    fun displayBodyError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertBodyErrorDisplayed()
        }
    }

    @Test
    fun displayHeaderError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.dashboard_progress_bar),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertHeaderErrorDisplayed()
        }
    }
}