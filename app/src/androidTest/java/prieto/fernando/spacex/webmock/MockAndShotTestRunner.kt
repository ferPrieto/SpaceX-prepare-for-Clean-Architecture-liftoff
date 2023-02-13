package prieto.fernando.spacex.webmock

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.system.Os
import androidx.test.runner.AndroidJUnitRunner
import com.karumi.shot.AndroidStorageInfo
import com.karumi.shot.compose.ComposeScreenshotRunner
import dagger.hilt.android.testing.HiltTestApplication

class MockAndShotTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
        configureFacebookLibFolder()
        ComposeScreenshotRunner.onCreate(this)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        ComposeScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }

    // This env var configuration is needed to make facebook's library
    // point at the folder we can use in API 29+ to save screenshots.
    // Without this hack, their library would only work on API 28-.
    private fun configureFacebookLibFolder() {
        Os.setenv("EXTERNAL_STORAGE", AndroidStorageInfo.storageBaseUrl, true)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
