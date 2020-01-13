package prieto.fernando.data_api.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core_android_test.MainCoroutineRule
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper

@RunWith(MockitoJUnitRunner::class)
class SpaceXRemoteSourceImplTest {
    private lateinit var cut: SpaceXRemoteSource

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper

    @Mock
    lateinit var launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        cut = SpaceXRemoteSourceImpl(
            apiService,
            companyInfoRepositoryMapper,
            launchesRepositoryMapper
        )
    }

    @Test
    fun `When getCompanyInfo then apiService invoked`() {
        runBlockingTest {
            // When
            whenever(apiService.getCompanyInfo()).thenReturn(mock())

            cut.getCompanyInfo()

            // Then
            verify(apiService, times(1)).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then apiService invoked`() {
        runBlockingTest {
            // When
            whenever(apiService.getAllLaunches()).thenReturn(mock())

            cut.getAllLaunches()

            // Then
            verify(apiService, times(1)).getAllLaunches()
        }
    }
}
