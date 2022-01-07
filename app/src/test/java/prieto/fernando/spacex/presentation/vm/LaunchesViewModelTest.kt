package prieto.fernando.spacex.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core_android_test.util.buildDate
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.screens.launches.LaunchUiModel
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import prieto.fernando.spacex.presentation.screens.launches.RocketUiModel
import prieto.fernando.spacex.presentation.vm.mapper.ClickableLinkProvider
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest {

    private lateinit var cut: LaunchesViewModel

    @Mock
    lateinit var getLaunches: GetLaunches

    @Mock
    lateinit var launchesMapper: LaunchesDomainToUiModelMapper

    @Mock
    lateinit var clickableLinkProvider: ClickableLinkProvider

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = LaunchesViewModel(getLaunches, launchesMapper,clickableLinkProvider)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `When launches Then launchUiModelRetrieved with expected result`() {
        runBlockingTest {
            // Given
            val launchDomainModels = listOf(
                LaunchDomainModel(
                    missionName = "missionName",
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
                LaunchUiModel(
                    "missionName",
                    "11-12-2019 at 12:00",
                    true,
                    "0",
                    RocketUiModel("rocketName", "rocketType"),
                    LinksUiModel(
                        "patchLink",
                        "wikipediaLink",
                        "videoLink"
                    ),
                    false
                ),
                LaunchUiModel(
                    "missionName2",
                    "07-12-2020 at 12:00",
                    false,
                    "361",
                    RocketUiModel("rocketName2", "rocketType2"),
                    LinksUiModel(
                        "patchLink2",
                        "wikipediaLink2",
                        "videoLink2"
                    ),
                    false
                )
            )
            val flow = flow {
                emit(launchDomainModels)
            }
            whenever(getLaunches.execute(0, false)).thenReturn(flow)
            whenever(launchesMapper.toUiModel(launchDomainModels)).thenReturn(expected)

            // When
            cut.launches()
            val actualValue = cut.viewState.value.launchUiModels

            // Then
            verify(getLaunches, times(2)).execute(0, false)
            assertEquals(expected, actualValue)
        }
    }

    @Test
    fun `Given Error When launches Then expected error state`() {
        runBlockingTest {
            // Given
            val expectedErrorState = true
            val flow = flow {
                emit(throw Exception("Network Exception"))
            }
            whenever(getLaunches.execute(0, false)).thenReturn(flow)

            // When
            cut.launches()
            val actualValue = cut.viewState.value.isError

            // Then
            verify(getLaunches, times(2)).execute(0, false)
            assertEquals(expectedErrorState, actualValue)
        }
    }
}
