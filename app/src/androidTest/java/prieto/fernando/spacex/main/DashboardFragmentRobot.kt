package prieto.fernando.spacex.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.not
import prieto.fernando.spacex.R
import prieto.fernando.spacex.utils.RecyclerViewItemCountAssertion
import prieto.fernando.spacex.utils.RecyclerViewMatcher

fun dashboardFragmentRobot(func: DashboardFragmentRobot.() -> Unit) =
    DashboardFragmentRobot().apply { func() }

class DashboardFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
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

    fun assertProgressBarBodyIsDisplayed() = apply {
        onView(progressBarBodyViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertProgressBarHeaderIsNotDisplayed() = apply {
        onView(progressBarHeaderViewMatcher).check(
            matches(
                not(isDisplayed())
            )
        )
    }

    fun assertToolbarIsDisplayed() = apply {
        onView(toolbarViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertFilterButtonIsDisplayed() = apply {
        onView(filterButtonViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertBodyErrorDisplayed() = apply {
        onView(bodyErrorViewMatcher).check(matches(isDisplayed()))
    }

    fun assertHeaderErrorDisplayed() = apply {
        onView(headerErrorViewMatcher).check(matches(isDisplayed()))
    }

    fun dialogYearViewMatcher() = apply {
        onView(dialogYearViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun youtubeIconViewMatcher() = apply {
        onView(youtubeIconViewMatcher).check(
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

        private val progressBarBodyViewMatcher = withId(R.id.progress_bar_body)

        private val progressBarHeaderViewMatcher = withId(R.id.progress_bar_header)

        private val toolbarViewMatcher = withId(R.id.toolbar)

        private val filterButtonViewMatcher = withId(R.id.filter)

        private val dialogYearViewMatcher = withId(R.id.dialog_year)

        private val youtubeIconViewMatcher = withId(R.id.youtube_icon)

        private val bodyErrorViewMatcher = withId(R.id.body_error_description)

        private val headerErrorViewMatcher = withId(R.id.header_error_description)
    }
}