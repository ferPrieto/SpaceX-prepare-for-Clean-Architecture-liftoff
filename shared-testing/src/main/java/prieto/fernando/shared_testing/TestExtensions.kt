package prieto.fernando.shared.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest

/**
 * Run a test with coroutines and a timeout
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun runTestWithTimeout(
    timeout: Long = 10_000L,
    testBody: suspend TestScope.() -> Unit
) = runTest(dispatchTimeoutMs = timeout, testBody = testBody)

/**
 * Extension to advance time and run pending coroutines
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun TestScope.advanceTimeAndRun(delayMillis: Long) {
    advanceTimeBy(delayMillis)
    testScheduler.runCurrent()
}

