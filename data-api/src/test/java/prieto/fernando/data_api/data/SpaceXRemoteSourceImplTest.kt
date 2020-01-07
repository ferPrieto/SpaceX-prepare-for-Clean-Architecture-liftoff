package prieto.fernando.data_api.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import prieto.fernando.data.SpaceXRemoteSource

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
        // When
        whenever(apiService.getCompanyInfo())
            .thenReturn(Single.just(mock()))

        cut.getCompanyInfo()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(apiService, times(1)).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then apiService invoked`() {
        // When
        whenever(apiService.getAllLaunches())
            .thenReturn(Single.just(mock()))

        cut.getAllLaunches()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(apiService, times(1)).getAllLaunches()
        }
    }
}
