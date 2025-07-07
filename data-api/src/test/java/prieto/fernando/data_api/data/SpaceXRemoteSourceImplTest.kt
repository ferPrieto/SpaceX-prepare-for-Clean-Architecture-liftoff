package prieto.fernando.data_api.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import prieto.fernando.core_android_test.TestCoroutineRule
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import prieto.fernando.data.model.LinksRepositoryModel
import prieto.fernando.data.model.RocketRepositoryModel
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import prieto.fernando.data_api.model.CompanyInfoResponse
import prieto.fernando.data_api.model.LaunchesResponse
import prieto.fernando.data_api.model.LinksResponse
import prieto.fernando.data_api.model.RocketResponse

@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImplTest {
    private lateinit var cut: SpaceXRemoteSource

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper

    @Inject
    lateinit var launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        apiService = mockk()
        companyInfoRepositoryMapper = mockk()
        launchesRepositoryMapper = mockk()

        cut = SpaceXRemoteSourceImpl(
            apiService,
            companyInfoRepositoryMapper,
            launchesRepositoryMapper
        )
    }

    @Test
    fun `When getCompanyInfo then apiService invoked`() = testCoroutineRule.runBlockingTest {
        // Given
        coEvery { apiService.getCompanyInfo() } returns CompanyInfoResponse(
            name = "SpaceX",
            founder = "Ellon",
            founded = "1999",
            employees = "Some random employees",
            launchSites = 1,
            valuation = 100L
        )
        coEvery { companyInfoRepositoryMapper.toRepositoryModel(apiService.getCompanyInfo()) } returns CompanyInfoRepositoryModel(
            name = "SpaceX",
            founder = "Ellon",
            founded = "1999",
            employees = "Some random employees",
            launchSites = 1,
            valuation = 100L
        )

        // When
        cut.getCompanyInfo()

        // Then
        coVerify(exactly = 1) { apiService.getCompanyInfo() }
    }

    @Test
    fun `When getAllLaunches then apiService invoked`() = testCoroutineRule.runBlockingTest {
        // Given    
        val linksResponse = LinksResponse(
            missionPatchSmall = "Some mission patch",
            wikipedia = "Link to wikipedia",
            videoLink = "Link to Youtube"
        )
        val rocketResponse = RocketResponse(
            rocketName = "Rocket first",
            rocketType = "Type1"
        )
        val linksRepositoryModel = LinksRepositoryModel(
            missionPatchSmall = "Some mission patch",
            wikipedia = "Link to wikipedia",
            videoLink = "Link to Youtube"
        )
        val rocketRepositoryModel = RocketRepositoryModel(
            rocketName = "Rocket first",
            rocketType = "Type1"
        )
        
        // When
        coEvery { apiService.getAllLaunches() } returns listOf(
            LaunchesResponse(
                missionName = "First mission",
                launchDate = "Some date",
                rocket = rocketResponse,
                links = linksResponse,
                launchSuccess = true
            )
        )
        coEvery { launchesRepositoryMapper.toRepositoryModel(apiService.getAllLaunches()) } returns listOf(
            LaunchRepositoryModel(
                missionName = "First mission",
                launchDateLocal = DateTime.now(),
                rocket = rocketRepositoryModel,
                links = linksRepositoryModel,
                launchSuccess = true
            )
        )

        cut.getAllLaunches()

        // Then
        coVerify(exactly = 1) { apiService.getAllLaunches() }
    }
}
