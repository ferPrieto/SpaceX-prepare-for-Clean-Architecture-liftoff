package prieto.fernando.spacex.presentation.screens.base

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import prieto.fernando.spacex.presentation.EntryPointActivity

internal abstract class BaseRobotScreen constructor(
    protected val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<EntryPointActivity>, EntryPointActivity>
) {

    protected fun getString(@StringRes resourceId: Int) =
        composeTestRule.activity.getString(resourceId)
}