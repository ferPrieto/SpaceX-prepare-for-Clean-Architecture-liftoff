package prieto.fernando.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import prieto.fernando.core_android_test.MainCoroutineRule
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @Inject
    lateinit var spaceXRepository: SpaceXRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        spaceXRepository = mockk()
        cut = GetCompanyInfoImpl(spaceXRepository)
    }

    @Test
    fun `When execute then returns expected CompanyInfoUiModel`() {
        runBlocking {
            // Given
            val companyInfoDomainModel = CompanyInfoDomainModel(
                "name",
                "founder",
                "foundedYear",
                "employees",
                1,
                30000
            )
            coEvery { spaceXRepository.getCompanyInfo() } returns flowOf(companyInfoDomainModel)

            // When
            cut.execute().first()

            // Then
            coVerify(exactly = 1) { spaceXRepository.getCompanyInfo() }
        }
    }
}
