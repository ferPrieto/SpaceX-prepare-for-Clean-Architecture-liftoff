package prieto.fernando.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import prieto.fernando.core_android_test.MainCoroutineRule
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import kotlin.test.assertEquals

@FlowPreview
@ExperimentalCoroutinesApi
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @MockK
    lateinit var spaceXRepository: SpaceXRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
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
            val channelCompanyInfo = ConflatedBroadcastChannel<CompanyInfoDomainModel>()
            channelCompanyInfo.offer(companyInfoDomainModel)
            val expected = CompanyInfoDomainModel(
                "name",
                "founder",
                "foundedYear",
                "employees",
                1,
                30000
            )
            coEvery { spaceXRepository.getCompanyInfo() } returns channelCompanyInfo.asFlow()


            // When
            val actualValue = cut.execute().first()

            // Then
            coVerify(exactly = 1) { spaceXRepository.getCompanyInfo() }
            assertEquals(expected, actualValue)
        }
    }
}
