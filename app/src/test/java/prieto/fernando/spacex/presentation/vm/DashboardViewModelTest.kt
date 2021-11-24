package prieto.fernando.spacex.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core.event.Event
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.spacex.presentation.dashboard.CompanyInfo
import prieto.fernando.spacex.presentation.launches.Launch
import prieto.fernando.spacex.presentation.launches.Links
import prieto.fernando.spacex.presentation.launches.Rocket

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {
    private lateinit var cut: DashboardViewModel

    @Mock
    lateinit var getLaunches: GetLaunches

    @Mock
    lateinit var getCompanyInfo: GetCompanyInfo

    @Mock
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper

    @Mock
    lateinit var launchesMapper: LaunchesDomainToUiModelMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = DashboardViewModelImpl(getLaunches, getCompanyInfo, companyInfoMapper, launchesMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `When launches then launchUiModelRetrieved with expected result`() {
        runBlockingTest {
            // Given
            val launchUiModelRetrievedTestObserver = mock<Observer<List<Launch>>>()
            cut.launches.observeForever(launchUiModelRetrievedTestObserver)
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
                Launch(
                    "missionName",
                    "11-12-2019 at 12:00",
                    true,
                    "0",
                    Rocket("rocketName", "rocketType"),
                    Links(
                        "patchLink",
                        "wikipediaLink",
                        "videoLink"
                    ),
                    false
                ),
                Launch(
                    "missionName2",
                    "07-12-2020 at 12:00",
                    false,
                    "361",
                    Rocket("rocketName2", "rocketType2"),
                    Links(
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
            whenever(getLaunches.execute(-1, false)).thenReturn(flow)
            whenever(launchesMapper.toUiModel(launchDomainModels)).thenReturn(expected)

            // When
            cut.launches()
            val actualValue = cut.launches.value

            // Then
            verify(getLaunches, times(1)).execute(-1, false)
            assertEquals(expected, actualValue)
        }
    }

    @Test
    fun `When companyInfo then companyInfoUiModelRetrieved with expected result`() {
        runBlockingTest {
            // Given
            val companyInfoUiModelRetrievedTestObserver = mock<Observer<CompanyInfo>>()
            cut.companyInfo.observeForever(companyInfoUiModelRetrievedTestObserver)
            val companyInfoDomainModel = CompanyInfoDomainModel(
                "name",
                "founder",
                "foundedYear",
                "employees",
                1,
                23
            )
            val expected = CompanyInfo(
                "name",
                "founder",
                "founded",
                "employees",
                1,
                23
            )
            val flow = flow {
                emit(companyInfoDomainModel)
            }
            whenever(getCompanyInfo.execute()).thenReturn(flow)
            whenever(companyInfoMapper.toUiModel(companyInfoDomainModel)).thenReturn(expected)

            // When
            cut.companyInfo()
            val actualValue = cut.companyInfo.value

            // Then
            verify(getCompanyInfo, times(1)).execute()
            assertEquals(expected, actualValue)
        }
    }

    @Test
    fun `When openLink then onOpenLink invoked with expected result`() {
        runBlockingTest {
            // Given
            val link = "Some cool space related link"
            val onOpenLinkTestObserver = mock<Observer<Event<String>>>()
            val expected = "Some cool space related link"
            cut.openLink.observeForever(onOpenLinkTestObserver)

            // When
            cut.openLink(link)
            val actualValue = cut.openLink.value?.peekContent()

            // Then
            assertEquals(expected, actualValue)
        }
    }
}
