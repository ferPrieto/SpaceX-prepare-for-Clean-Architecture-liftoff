package prieto.fernando.data.repository

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
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper

@RunWith(MockitoJUnitRunner::class)
class SpaceXRepositoryImplTest {
    private lateinit var cut: SpaceXRepositoryImpl

    @Mock
    lateinit var spaceXRemoteSource: SpaceXRemoteSource

    @Mock
    lateinit var companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper

    @Mock
    lateinit var launchesDomainMapper: LaunchesRepositoryToDomainModelMapper

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        cut = SpaceXRepositoryImpl(spaceXRemoteSource, companyInfoDomainMapper, launchesDomainMapper)
    }

    @Test
    fun `When getCompanyInfo then spaceXRemoteSource invoked`() {
        // When
        whenever(spaceXRemoteSource.getCompanyInfo())
            .thenReturn(Single.just(mock()))

        cut.getCompanyInfo()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(spaceXRemoteSource, times(1)).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then spaceXRemoteSource invoked`() {
        // When
        whenever(spaceXRemoteSource.getCompanyInfo())
            .thenReturn(Single.just(mock()))

        cut.getCompanyInfo()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(spaceXRemoteSource, times(1)).getCompanyInfo()
        }
    }
}
