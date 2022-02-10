package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import prieto.fernando.spacex.presentation.EntryPointActivity

internal fun launchesScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<EntryPointActivity>, EntryPointActivity>,
    func: LaunchesScreenRobot.() -> Unit
) = LaunchesScreenRobot(composeTestRule).also { func }

internal open class LaunchesScreenRobot constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<EntryPointActivity>, EntryPointActivity>
) {

    private val launchesTabItem by lazy {
        composeTestRule.onNodeWithText(
            "Launches"
        )
    }

    private val launchesTitle by lazy {
        composeTestRule.onNodeWithText(
            "LAUNCHES",
            useUnmergedTree = true
        )
    }

    private val launchesListItems by lazy {
        composeTestRule.onAllNodesWithContentDescription(
            "Item",
            substring = true,
            useUnmergedTree = true
        )
    }

    private val connectionErrorMessage by lazy {
        composeTestRule.onNodeWithText("AN ERROR OCCURRED", useUnmergedTree = true)
    }

    private val connectionErrorAnimation by lazy {
        composeTestRule.onNodeWithContentDescription("404 Animation")
    }

    private val dialogFilterButton by lazy {
        composeTestRule.onNodeWithContentDescription("Filter Button")
    }

    private val dialogText by lazy {
        composeTestRule.onNodeWithText("FILTER BY YEAR", useUnmergedTree = true)
    }

    private val noResultsText by lazy {
        composeTestRule.onNodeWithText("NO RESULTS FOUND", useUnmergedTree = true)
    }

    private val missionOneText by lazy {
        composeTestRule.onNodeWithText("Mission1", useUnmergedTree = true)
    }

    private val missionTwoText by lazy {
        composeTestRule.onNodeWithText("Mission2", useUnmergedTree = true)
    }

    fun clickOnLaunchesTab() = launchesTabItem.assertIsDisplayed().performClick()

    fun initialElementsShowed() {
        launchesTitle.assertIsDisplayed()
        dialogFilterButton.assertIsDisplayed()
    }

    fun listItemsShowed(numItemsShowed: Int) = launchesListItems.assertCountEquals(numItemsShowed)

    fun advanceTimeBy(timeToAdvance: Long) = composeTestRule.mainClock.advanceTimeBy(timeToAdvance)

    fun errorElementsDisplayed() {
        connectionErrorMessage.assertExists().assertIsDisplayed()
        connectionErrorAnimation.assertExists().assertIsDisplayed()
    }

    fun dialogElementsDisplayed() {
        dialogFilterButton.assertExists().assertIsDisplayed().performClick()
        dialogText.assertExists().assertIsDisplayed()
    }

    fun noResultsElementsShowed() {
        noResultsText.assertExists().assertIsDisplayed()
        listItemsShowed(0)
    }

    fun missionOneAndTwoShowed() {
        missionOneText.assertIsDisplayed()
        missionTwoText.assertIsDisplayed()
    }
}
