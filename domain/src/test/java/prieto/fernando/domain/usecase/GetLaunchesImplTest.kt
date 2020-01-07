package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel

@RunWith(MockitoJUnitRunner::class)
class GetLaunchesImplTest {
    private lateinit var cut: GetLaunchesImpl

    @Mock
    lateinit var spaceXRepository: SpaceXRepository


    @Mock
    lateinit var launchesDomainFilter: LaunchesDomainFilter

    @Before
    fun setUp() {
        cut = GetLaunchesImpl(spaceXRepository, launchesDomainFilter)
    }

    @Test
    fun `When execute then returns expected launchUiModels`() {
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

        whenever(spaceXRepository.getAllLaunches()).thenReturn(Single.just(launchDomainModels))
        whenever(launchesDomainFilter.filter(launchDomainModels, -1, false)).thenReturn(
            launchDomainModels
        )

        // When
        val actualValue = cut.execute(-1, false)

        // Then
        actualValue
            .test()
            .assertResult(expected)
            .assertComplete()
            .assertNoErrors()
    }

    private fun buildDate(dateValue: String) =
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .parseDateTime(dateValue.replace("Z", "+0000"))
}
