package prieto.fernando.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import prieto.fernando.core_android_test.MainCoroutineRule
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import prieto.fernando.data.model.LinksRepositoryModel
import prieto.fernando.data.model.RocketRepositoryModel
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SpaceXRepositoryImplTest {
    private lateinit var cut: SpaceXRepositoryImpl

    @Inject
    lateinit var spaceXRemoteSource: SpaceXRemoteSource

    @Inject
    lateinit var companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper

    @Inject
    lateinit var launchesDomainMapper: LaunchesRepositoryToDomainModelMapper

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        spaceXRemoteSource = mockk()
        companyInfoDomainMapper = mockk()
        launchesDomainMapper = mockk()
        cut =
            SpaceXRepositoryImpl(spaceXRemoteSource, companyInfoDomainMapper, launchesDomainMapper)
    }

    @Test
    fun `When getCompanyInfo then spaceXRemoteSource invoked`() {
        runBlockingTest {
            val companyInfoRepositoryModel = CompanyInfoRepositoryModel(
                name = "SpaceX",
                founder = "Ellon",
                founded = "1999",
                employees = "Some random employees",
                launchSites = 1,
                valuation = 100L
            )
            val expected = CompanyInfoDomainModel(
                name = "SpaceX",
                founder = "Ellon",
                founded = "1999",
                employees = "Some random employees",
                launchSites = 1,
                valuation = 100L
            )
            // When
            coEvery { spaceXRemoteSource.getCompanyInfo() } returns flow {
                emit(
                    companyInfoRepositoryModel
                )
            }
            coEvery { companyInfoDomainMapper.toDomainModel(companyInfoRepositoryModel) } returns expected

            val flowActual = cut.getCompanyInfo()

            // Then
            coVerify(exactly = 1) {
                spaceXRemoteSource.getCompanyInfo()
            }
            flowActual.collect { actual: CompanyInfoDomainModel ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `When getAllLaunches then spaceXRemoteSource invoked`() {
        runBlockingTest {
            val date = DateTime.now()
            val linksRepositoryModel = LinksRepositoryModel(
                missionPatchSmall = "Some mission patch",
                wikipedia = "Link to wikipedia",
                videoLink = "Link to Youtube"
            )
            val rocketRepositoryModel = RocketRepositoryModel(
                rocketName = "Rocket first",
                rocketType = "Type1"
            )
            val launchRepositoryModel = LaunchRepositoryModel(
                missionName = "First mission",
                launchDateLocal = date,
                rocket = rocketRepositoryModel,
                links = linksRepositoryModel,
                launchSuccess = true
            )
            val rocketDomainModel = RocketDomainModel(
                rocketName = "Rocket first",
                rocketType = "Type1"
            )
            val linksDomainModel = LinksDomainModel(
                missionPatchSmall = "Some mission patch",
                wikipedia = "Link to wikipedia",
                videoLink = "Link to Youtube"
            )
            val expected = listOf(
                LaunchDomainModel(
                    missionName = "First mission",
                    launchDate = date,
                    rocket = rocketDomainModel,
                    links = linksDomainModel,
                    launchSuccess = true
                )
            )
            // When
            coEvery { spaceXRemoteSource.getAllLaunches() } returns flow {
                emit(
                    listOf(launchRepositoryModel)
                )
            }
            coEvery { launchesDomainMapper.toDomainModel(listOf(launchRepositoryModel)) } returns expected
            val flowActual = cut.getAllLaunches()

            // Then
            coVerify(exactly = 1) { spaceXRemoteSource.getAllLaunches() }
            flowActual.collect { actual: List<LaunchDomainModel> ->
                assertEquals(expected, actual)
            }
        }
    }
}
