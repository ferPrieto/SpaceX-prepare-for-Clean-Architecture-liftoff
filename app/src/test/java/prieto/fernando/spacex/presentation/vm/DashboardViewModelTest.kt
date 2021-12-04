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
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.spacex.presentation.screens.dashboard.CompanyInfoUiModel
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {
    private lateinit var cut: DashboardViewModel

    @Mock
    lateinit var getCompanyInfo: GetCompanyInfo

    @Mock
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = DashboardViewModel(getCompanyInfo, companyInfoMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `When companyInfo Then companyInfoUiModelRetrieved with expected result`() {
        runBlockingTest {
            // Given
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
            val flow = flow {
                emit(companyInfoDomainModel)
            }
            whenever(getCompanyInfo.execute()).thenReturn(flow)
            whenever(companyInfoMapper.toUiModel(companyInfoDomainModel)).thenReturn(expected)

            // When
            cut.companyInfo()
            val actualValue = cut.viewState.value.companyInfoUiModel

            // Then
            verify(getCompanyInfo, times(2)).execute()
            assertEquals(expected, actualValue)
        }
    }

    @Test
    fun `Given Error When companyInfo Then expected error state`() {
        runBlockingTest {
            // Given
            val expectedErrorState = true
            val flow = flow {
                emit(throw Exception("Network Exception"))
            }
            whenever(getCompanyInfo.execute()).thenReturn(flow)

            // When
            cut.companyInfo()
            val actualValue = cut.viewState.value.isError

            // Then
            verify(getCompanyInfo, times(2)).execute()
            assertEquals(expectedErrorState, actualValue)
        }
    }

}
