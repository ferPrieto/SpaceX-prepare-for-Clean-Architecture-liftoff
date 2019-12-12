package prieto.fernando.spacex.main

import androidx.test.espresso.Espresso.onView
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

    fun clickItem(position: Int) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(ViewActions.click())
    }

    companion object {

        private const val recyclerViewId = R.id.launches_recycler_view

        private val recyclerViewMatcher = withId(R.id.launches_recycler_view)

        private val progressBarBodyViewMatcher = withId(R.id.progress_bar_body)

        private val progressBarHeaderViewMatcher = withId(R.id.progress_bar_header)
    }
}