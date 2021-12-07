package prieto.fernando.spacex.launches

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchesFragmentTest {
/*
    @get:Rule
    val activityTestRule = ActivityTestRule(EntryPointActivity::class.java, true, true)

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
    }*/
}