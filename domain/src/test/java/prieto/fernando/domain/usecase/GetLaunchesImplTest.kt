package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core_android_test.util.buildDate
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
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
            val launchesChannel = ConflatedBroadcastChannel<List<LaunchDomainModel>>()
            launchesChannel.offer(launchDomainModels)
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

            whenever(spaceXRepository.getAllLaunches()).thenReturn(launchesChannel.asFlow())
            whenever(launchesDomainFilter.filter(launchDomainModels, -1, false)).thenReturn(
                launchDomainModels
            )

            // When
            val actualValue = cut.execute(-1, false).first()

            // Then
            verify(spaceXRepository, times(1)).getAllLaunches()
            assertEquals(expected, actualValue)
        }
    }
}
