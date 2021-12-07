package prieto.fernando.spacex.dashboard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.not
import prieto.fernando.spacex.R
import prieto.fernando.spacex.utils.BottomNavigationMatcher
import prieto.fernando.spacex.utils.RecyclerViewMatcher

fun dashboardFragmentRobot(func: DashboardFragmentRobot.() -> Unit) =
    DashboardFragmentRobot().apply { func() }

class DashboardFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }
/*

    fun assertCompanyTitleIsDisplayed() = apply {
        onView(companyTitleViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertCompanyDescriptionIsDisplayed() = apply {
        onView(companyDescriptionViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertDashboardAnimationIsDisplayed() = apply {
        onView(dashboardAnimationViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertBottomNavigationIsDisplayed() = apply {
        onView(bottomNavigationViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun dashboardTabChecked() = apply {
        BottomNavigationMatcher().withBottomNavItemCheckedStatus(true).let { dashboardTabMatcher ->
            onView(dashboardTabViewMatcher).check(matches(dashboardTabMatcher))
        }
    }

    fun launchesTabNotChecked() = apply {
        BottomNavigationMatcher().withBottomNavItemCheckedStatus(false).let { launchesTabMatcher ->
            onView(launchesTabViewMatcher).check(matches(launchesTabMatcher))
        }
    }

    fun assertProgressBarIsNotDisplayed() = apply {
        onView(progressBarViewMatcher).check(
            matches(
                not(isDisplayed())
            )
        )
    }

    fun assertDescriptionErrorDisplayed() = apply {
        onView(errorDescriptionViewMatcher).check(matches(isDisplayed()))
    }

    fun clickLaunchesTab() = apply {
        onView(launchesTabViewMatcher).perform(ViewActions.click())
    }

*/

    companion object {

     /*   private val companyTitleViewMatcher = withId(R.id.company_title)

        private val companyDescriptionViewMatcher = withId(R.id.company_description)

        private val dashboardAnimationViewMatcher = withId(R.id.dashboard_animation)

        private val progressBarViewMatcher = withId(R.id.dashboard_progress_bar)

        private val errorDescriptionViewMatcher = withId(R.id.error_description)

        private val bottomNavigationViewMatcher = withId(R.id.bottom_navigation)

        private val dashboardTabViewMatcher = withId(R.id.dashboard)

        private val launchesTabViewMatcher = withId(R.id.launches)*/
    }
}