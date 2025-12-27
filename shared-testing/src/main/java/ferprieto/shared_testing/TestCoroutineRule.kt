package ferprieto.shared.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule(
    val testCoroutineDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestRule {
    val testCoroutineScope = TestScope(testCoroutineDispatcher)

    override fun apply(
        base: Statement,
        description: Description?,
    ) =
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)

                base.evaluate()

                try {
                    Dispatchers.resetMain()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                testCoroutineScope.cancel()
            }
        }

    fun runBlockingTest(block: suspend TestScope.() -> Unit) = testCoroutineScope.runTest(dispatchTimeoutMs = 10_000L, testBody = block)

    fun runCurrent() = testCoroutineDispatcher.scheduler.runCurrent()

    fun advanceUntilIdle() = testCoroutineDispatcher.scheduler.advanceUntilIdle()
}
