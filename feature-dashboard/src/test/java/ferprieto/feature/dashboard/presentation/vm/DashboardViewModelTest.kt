package ferprieto.feature.dashboard.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ferprieto.feature.dashboard.domain.model.CompanyInfoDomainModel
import ferprieto.feature.dashboard.domain.usecase.GetCompanyInfo
import ferprieto.feature.dashboard.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import ferprieto.feature.dashboard.presentation.ui.CompanyInfoUiModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DashboardViewModelTest {
    private lateinit var cut: DashboardViewModel

    @Inject
    lateinit var getCompanyInfo: GetCompanyInfo

    @Inject
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        getCompanyInfo = mockk()
        companyInfoMapper = mockk()
        cut = DashboardViewModel(getCompanyInfo, companyInfoMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `When companyInfo Then companyInfoUiModelRetrieved with expected result`() {
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

        coEvery { getCompanyInfo.execute() } returns flow {
            emit(companyInfoDomainModel)
        }
        every { companyInfoMapper.toUiModel(companyInfoDomainModel) } returns expected

        // When
        cut.companyInfo()
        val actual = cut.viewState.value.companyInfoUiModel

        // Then
        coVerify(exactly = 2) { getCompanyInfo.execute() }

        assertEquals(expected, actual)
    }

    @Test(expected = Throwable::class)
    fun `Given Error When companyInfo Then expected error state`() {
        runTest {
            // Given
            var exceptionThrown = false

            // When
            coEvery { getCompanyInfo.execute() } throws java.lang.Exception("Network exception")
            try {
                cut.companyInfo()
            } catch (exception: Exception) {
                exceptionThrown = true
            }
            val actual = cut.viewState.value.isError

            // Then
            assertEquals(exceptionThrown, actual)
        }
    }
}

