package prieto.fernando.shared.testing

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

/**
 * Base class for tests using MockK that automatically handles initialization and cleanup
 */
abstract class MockKTest {
    
    @Before
    fun setUpMocks() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        setUp()
    }
    
    @After
    fun tearDownMocks() {
        tearDown()
        unmockkAll()
    }
    
    /**
     * Override to add custom setup logic
     */
    protected open fun setUp() {}
    
    /**
     * Override to add custom teardown logic
     */
    protected open fun tearDown() {}
}

