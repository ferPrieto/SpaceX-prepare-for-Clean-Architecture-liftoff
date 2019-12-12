package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.presentation.model.CompanyInfoUiModel

@RunWith(MockitoJUnitRunner::class)
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @Mock
    lateinit var spaceXRepository: SpaceXRepository

    @Mock
    lateinit var companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper

    @Before
    fun setUp() {
        cut = GetCompanyInfoImpl(spaceXRepository, companyInfoDomainToUiModelMapper)
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
        val expected = CompanyInfoUiModel(
            "name",
            "founder",
            "foundedYear",
            "employees",
            1,
            30000
        )
        whenever(spaceXRepository.getCompanyInfo()).thenReturn(Single.just(companyInfoDomainModel))
        whenever(companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)).thenReturn(
            expected
        )

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
