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
import prieto.fernando.spacex.dashboard.dashboardFragmentRobot
import prieto.fernando.spacex.presentation.MainActivity
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

    private var launchesAnimationGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(launchesAnimationGoneIdlingResource)
    }

    @Test
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesAnimationGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.launches_animation),
                View.GONE
            )

        launchesFragmentRobot {
            assertLaunchesTitleIsDisplayed()
            waitForCondition(launchesAnimationGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            dashboardTabIsNotChecked()
            launchesTabChecked()
        }
    }

    @Test
    fun itemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesAnimationGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.launches_animation),
                View.GONE
            )

        launchesFragmentRobot {
            waitForCondition(launchesAnimationGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            assertItemsSize()
        }
    }

    @Test
    fun clickItemShowBottomSheet() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesAnimationGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.launches_animation),
                View.GONE
            )

        launchesFragmentRobot {
            waitForCondition(launchesAnimationGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickItem(3)
            youtubeViewMatcher()
        }
    }

    @Test
    fun clickItemAndShowDialog() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesAnimationGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.launches_animation),
                View.GONE
            )

        launchesFragmentRobot {
            waitForCondition(launchesAnimationGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickFilter()
            dialogYearViewMatcher()
        }
    }

    @Test
    fun displayBodyError() {
        mockWebServer.dispatcher = ErrorDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesAnimationGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.launches_animation),
                View.GONE
            )

        launchesFragmentRobot {
            waitForCondition(launchesAnimationGoneIdlingResource)
            assertBodyErrorDisplayed()
        }
    }
}