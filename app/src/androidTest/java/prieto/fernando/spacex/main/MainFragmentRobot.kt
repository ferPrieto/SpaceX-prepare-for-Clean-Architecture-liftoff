package prieto.fernando.spacex.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import prieto.fernando.spacex.R
import prieto.fernando.spacex.utils.RecyclerViewItemCountAssertion

fun infiniteFragmentRobot(func: MainFragmentRobot.() -> Unit) =
    MainFragmentRobot().apply { func() }

class MainFragmentRobot {

    fun assertRecyclerViewDisplayed() = apply {
        onView(allOf(recyclerViewMatcher, bodyContainerViewMatcher)).check(matches(isDisplayed()))
    }

    fun assertItemsSize() = apply {
        onView(recyclerViewMatcher).check(
            RecyclerViewItemCountAssertion(105)
        )
    }

    fun assertBodyDisplayed() = apply {
        onView(allOf(  withId(R.id.body_container))).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertProgressBarBodyDisplayed() = apply {
        onView(allOf(progressBarBodyViewMatcher, headerContainerViewMatcher)).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertProgressBarHeaderDisplayed() = apply {
        onView(allOf(progressBarHeaderViewMatcher, headerContainerViewMatcher)).check(
            matches(
                isDisplayed()
            )
        )
    }

    companion object {

        private val recyclerViewMatcher = withId(R.id.launches_recycler_view)

        private val progressBarBodyViewMatcher = withId(R.id.progress_bar_body)

        private val progressBarHeaderViewMatcher = withId(R.id.progress_bar_header)

        private val bodyContainerViewMatcher = isDescendantOfA(withId(R.id.body_container))

        private val headerContainerViewMatcher = isDescendantOfA(withId(R.id.header_container))
    }
}