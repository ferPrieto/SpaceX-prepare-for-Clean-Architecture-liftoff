package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel

@RunWith(MockitoJUnitRunner::class)
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @Mock
    lateinit var spaceXRepository: SpaceXRepository


    @Before
    fun setUp() {
        cut = GetCompanyInfoImpl(spaceXRepository)
    }

    @Test
    fun `When execute then returns expected CompanyInfoUiModel`() {
        // Given
        val companyInfoDomainModel = CompanyInfoDomainModel(
            "name",
            "founder",
            "foundedYear",
            "employees",
            1,
            30000
        )
        val expected = CompanyInfoDomainModel(
            "name",
            "founder",
            "foundedYear",
            "employees",
            1,
            30000
        )
        whenever(spaceXRepository.getCompanyInfo()).thenReturn(Single.just(companyInfoDomainModel))

        // When
        val actualValue = cut.execute()

        // Then
        actualValue
            .test()
            .assertResult(expected)
            .assertComplete()
            .assertNoErrors()
    }
}
