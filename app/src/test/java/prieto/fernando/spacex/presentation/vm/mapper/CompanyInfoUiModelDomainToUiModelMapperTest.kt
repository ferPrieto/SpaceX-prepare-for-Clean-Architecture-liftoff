package prieto.fernando.spacex.presentation.vm.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.spacex.presentation.screens.dashboard.CompanyInfoUiModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CompanyInfoUiModelDomainToUiModelMapperTest(
    private val givenCompanyInfo: CompanyInfoDomainModel,
    private val expected: CompanyInfoUiModel
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    ),
                    CompanyInfoUiModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    )
                ),
                arrayOf(
                    CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    ),
                    CompanyInfoUiModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    )
                ),
                arrayOf(
                    CompanyInfoDomainModel(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    ),
                    CompanyInfoUiModel(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    )
                )
            )
        }
    }

    private lateinit var cut: CompanyInfoDomainToUiModelMapperImpl

    @Before
    fun setUp() {
        cut = CompanyInfoDomainToUiModelMapperImpl()
    }

    @Test
    fun `Given companyInfoDomainModel when toUiModel then returns expected result`() {
        // When
        val actualValue = cut.toUiModel(givenCompanyInfo)

        // Then
        assertEquals(expected, actualValue)
    }
}
