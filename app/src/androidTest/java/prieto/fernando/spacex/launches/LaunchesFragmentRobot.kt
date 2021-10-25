package prieto.fernando.spacex.launches

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
import prieto.fernando.spacex.utils.RecyclerViewItemCountAssertion
import prieto.fernando.spacex.utils.RecyclerViewMatcher

fun launchesFragmentRobot(func: LaunchesFragmentRobot.() -> Unit) =
    LaunchesFragmentRobot().apply { func() }

class LaunchesFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertLaunchesTitleIsDisplayed() = apply {
        onView(launchesTitleViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertLaunchesAnimationIsDisplayed() = apply {
        onView(launchesAnimationViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertRecyclerViewIsNotDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(not(isDisplayed())))
    }

    fun assertRecyclerViewIsDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertItemsSize() = apply {
        onView(recyclerViewMatcher).check(
            RecyclerViewItemCountAssertion(104)
        )
    }

    fun dashboardTabIsNotChecked() = apply {
        BottomNavigationMatcher().withBottomNavItemCheckedStatus(false).let { dashboardTabMatcher ->
            onView(dashboardTabViewMatcher).check(matches(dashboardTabMatcher))
        }
    }

    fun launchesTabChecked() = apply {
        BottomNavigationMatcher().withBottomNavItemCheckedStatus(true).let { launchesTabMatcher ->
            onView(launchesTabViewMatcher).check(matches(launchesTabMatcher))
        }
    }

    fun assertFilterButtonIsDisplayed() = apply {
        onView(filterButtonViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertBodyErrorDisplayed() = apply {
        onView(launchesErrorViewMatcher).check(matches(isDisplayed()))
    }

    fun dialogYearViewMatcher() = apply {
        onView(dialogYearViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun youtubeViewMatcher() = apply {
        onView(youtubeViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun clickItem(position: Int) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(ViewActions.click())
    }

    fun clickFilter() = apply {
        onView(filterButtonViewMatcher).perform(ViewActions.click())
    }

    companion object {

        private const val recyclerViewId = R.id.launches_recycler_view

        private val recyclerViewMatcher = withId(R.id.launches_recycler_view)

        private val launchesTitleViewMatcher = withId(R.id.launches_title)

        private val launchesAnimationViewMatcher = withId(R.id.launches_animation)

        private val filterButtonViewMatcher = withId(R.id.launches_filter)

        private val dialogYearViewMatcher = withId(R.id.dialog_year)

        private val youtubeViewMatcher = withId(R.id.youtube_title)

        private val launchesErrorViewMatcher = withId(R.id.launches_error_description)

        private val errorDescriptionViewMatcher = withId(R.id.error_description)

        private val bottomNavigationViewMatcher = withId(R.id.bottom_navigation)

        private val dashboardTabViewMatcher = withId(R.id.dashboard)

        private val launchesTabViewMatcher = withId(R.id.launches)
    }
}