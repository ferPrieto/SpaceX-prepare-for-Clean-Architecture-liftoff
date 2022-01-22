package prieto.fernando.presentation

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
import prieto.fernando.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.model.CompanyInfoUiModel

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelImplTest {
    private lateinit var cut: DashboardViewModel

    @Mock
    lateinit var getCompanyInfo: GetCompanyInfo

    @Mock
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = DashboardViewModelImpl(getCompanyInfo, companyInfoMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `When companyInfo then companyInfoUiModelRetrieved with expected result`() {
        runBlockingTest {
            // Given
            val companyInfoUiModelRetrievedTestObserver = mock<Observer<CompanyInfoUiModel>>()
            cut.companyInfo.observeForever(companyInfoUiModelRetrievedTestObserver)
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
            val actualValue = cut.companyInfo.value

            // Then
            verify(getCompanyInfo, times(1)).execute()
            assertEquals(expected, actualValue)
        }
    }
}