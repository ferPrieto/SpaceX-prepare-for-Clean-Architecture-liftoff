package prieto.fernando.spacex.utils

import androidx.test.core.app.launchActivity
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import prieto.fernando.spacex.presentation.MainActivity
import prieto.fernando.spacex.webmock.injectTestConfiguration

class TestConfigurationRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

        injectTestConfiguration {}
        launchActivity<MainActivity>()
    }
}