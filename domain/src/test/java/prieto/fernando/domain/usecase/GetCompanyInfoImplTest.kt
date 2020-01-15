package prieto.fernando.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.core_android_test.MainCoroutineRule
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import kotlin.test.assertEquals

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @Mock
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
            whenever(spaceXRepository.getCompanyInfo()).thenReturn(channelCompanyInfo.asFlow())

            // When
            val actualValue = cut.execute().first()

            // Then
            verify(spaceXRepository, times(1)).getCompanyInfo()
            assertEquals(expected, actualValue)
        }
    }
}
