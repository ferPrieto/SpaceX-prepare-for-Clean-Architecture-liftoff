package prieto.fernando.spacex.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

  /*  @get:Rule
    val activityTestRule = ActivityTestRule(EntryPointActivity::class.java, true, true)

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
            assertCompanyTitleIsDisplayed()
            assertCompanyDescriptionIsDisplayed()
            assertDashboardAnimationIsDisplayed()
            assertProgressBarIsNotDisplayed()
            assertBottomNavigationIsDisplayed()
            dashboardTabChecked()
            launchesTabNotChecked()
        }
    }

    @Test
    fun clickBottomNavigationItemToLaunchesScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            clickLaunchesTab()
        }

        launchesFragmentRobot {
            assertLaunchesTitleIsDisplayed()
        }
    }

    @Test
      fun displayDashboardError() {
          mockWebServer.dispatcher = ErrorDispatcher()
          progressBarGoneIdlingResource =
              ViewVisibilityIdlingResource(
                  activityTestRule.activity.findViewById(R.id.dashboard_progress_bar),
                  View.GONE
              )

          dashboardFragmentRobot {
              waitForCondition(progressBarGoneIdlingResource)
              assertDescriptionErrorDisplayed()
          }
      }*/
}