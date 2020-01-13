package prieto.fernando.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core_android_test.util.buildDate
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.presentation.model.LinksUiModel
import prieto.fernando.presentation.model.RocketUiModel
import prieto.fernando.presentation.setup.setupViewModelForTests

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private lateinit var cut: MainViewModel

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
        cut = MainViewModel(getLaunches, getCompanyInfo, companyInfoMapper, launchesMapper)
        setupViewModelForTests(cut)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `When launches then launchUiModelRetrieved invoked with expected result`() {
        // Given
        val launchUiModelRetrievedTestObserver = mock<Observer<List<LaunchUiModel>>>()
        cut.onLaunchesUiModelRetrieved().observeForever(launchUiModelRetrievedTestObserver)
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
            LaunchUiModel(
                "missionName",
                "11-12-2019 at 12:00",
                true,
                "0",
                RocketUiModel("rocketName", "rocketType"),
                LinksUiModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchUiModel(
                "missionName2",
                "07-12-2020 at 12:00",
                false,
                "361",
                RocketUiModel("rocketName2", "rocketType2"),
                LinksUiModel("patchLink2", "wikipediaLink2", "videoLink2"),
                false
            )
        )
        whenever(getLaunches.execute(-1, false)).thenReturn(Single.just(launchDomainModels))
        whenever(launchesMapper.toUiModel(launchDomainModels)).thenReturn(expected)

        // When
        cut.launches()

        // Then
        val captor = ArgumentCaptor.forClass(LaunchUiModel::class.java)
        captor.run {
            verify(launchUiModelRetrievedTestObserver, times(1)).onChanged(listOf(capture()))
            assertEquals(expected, value)
        }
    }

    @Test
    fun `When companyInfo then companyInfoUiModelRetrieved invoked with expected result`() {
        // Given
        val companyInfoUiModelRetrievedTestObserver = mock<Observer<CompanyInfoUiModel>>()
        cut.onCompanyInfoUiModelRetrieved().observeForever(companyInfoUiModelRetrievedTestObserver)
        val companyInfoDomainModel = CompanyInfoDomainModel(
            "name",
            "founder",
            "foundedYear",
            "employees",
            1,
            23
        )
        val expected = CompanyInfoUiModel(
            "name",
            "founder",
            "founded",
            "employees",
            1,
            23
        )
        whenever(getCompanyInfo.execute()).thenReturn(Single.just(companyInfoDomainModel))
        whenever(companyInfoMapper.toUiModel(companyInfoDomainModel)).thenReturn(expected)

        // When
        cut.companyInfo()

        // Then
        val captor = ArgumentCaptor.forClass(CompanyInfoUiModel::class.java)
        captor.run {
            verify(companyInfoUiModelRetrievedTestObserver, times(1)).onChanged(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `When openLink then onOpenLink invoked with expected result`() {
        // Given
        val link = "Some cool space related link"
        val onOpenLinkTestObserver = mock<Observer<String>>()
        val expected = "Some cool space related link"
        cut.onOpenLink().observeForever(onOpenLinkTestObserver)

        // When
        cut.openLink(link)

        // Then
        val captor = ArgumentCaptor.forClass(String::class.java)
        captor.run {
            verify(onOpenLinkTestObserver, times(1)).onChanged(capture())
            assertEquals(expected, value)
        }
    }
}
