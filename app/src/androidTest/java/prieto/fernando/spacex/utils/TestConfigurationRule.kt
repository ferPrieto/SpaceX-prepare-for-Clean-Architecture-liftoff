package prieto.fernando.spacex.utils

import androidx.test.core.app.launchActivity
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import prieto.fernando.spacex.ui.MainActivity

class TestConfigurationRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        launchActivity<MainActivity>()
    }
}