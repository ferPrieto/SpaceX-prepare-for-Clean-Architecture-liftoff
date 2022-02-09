package prieto.fernando.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import prieto.fernando.core_android_test.util.buildDate
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import javax.inject.Inject
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetLaunchesImplTest {
    private lateinit var cut: GetLaunchesImpl

    @Inject
    lateinit var spaceXRepository: SpaceXRepository


    @Inject
    lateinit var launchesDomainFilter: LaunchesDomainFilter

    @Before
    fun setUp() {
        spaceXRepository = mockk()
        launchesDomainFilter = mockk()
        cut = GetLaunchesImpl(spaceXRepository, launchesDomainFilter)
    }

    @Test
    fun `When execute then returns expected launchUiModels`() {
        runBlocking {
            // Given
            val launchDomainModels = listOf(
                LaunchDomainModel(
                    "missionName",
                    buildDate("2019-12-11T12:00:00.000Z"),
                    RocketDomainModel("rocketName", "rocketType"),
                    LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                    false
                ),
                LaunchDomainModel(
                    "missionName2",
                    buildDate("2020-12-07T12:00:00.000Z"),
                    RocketDomainModel("rocketName2", "rocketType2"),
                    LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2"),
                    false
                )
            )
            val expected = listOf(
                LaunchDomainModel(
                    "missionName",
                    buildDate("2019-12-11T12:00:00.000Z"),
                    RocketDomainModel("rocketName", "rocketType"),
                    LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                    false
                ),
                LaunchDomainModel(
                    "missionName2",
                    buildDate("2020-12-07T12:00:00.000Z"),
                    RocketDomainModel("rocketName2", "rocketType2"),
                    LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2"),
                    false
                )
            )

            coEvery { spaceXRepository.getAllLaunches() } returns flowOf(launchDomainModels)
            coEvery {
                launchesDomainFilter.filter(
                    launchDomainModels,
                    -1,
                    false
                )
            } returns launchDomainModels

            // When
            val actualValue = cut.execute(-1, false).first()

            // Then
            coVerify(exactly = 1) { spaceXRepository.getAllLaunches() }
            assertEquals(expected, actualValue)
        }
    }
}
