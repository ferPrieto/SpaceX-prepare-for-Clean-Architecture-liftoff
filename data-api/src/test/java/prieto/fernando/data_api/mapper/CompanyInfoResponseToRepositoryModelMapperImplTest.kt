package prieto.fernando.data_api.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import prieto.fernando.data_api.model.CompanyInfoResponse
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CompanyInfoResponseToRepositoryModelMapperImplTest(
    private val givenCompanyInfo: CompanyInfoResponse,
    private val expected: CompanyInfoRepositoryModel
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    CompanyInfoResponse(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    ), CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    )
                ),
                arrayOf(
                    CompanyInfoResponse(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    ), CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    )
                ),
                arrayOf(
                    CompanyInfoResponse(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    ), CompanyInfoRepositoryModel(
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

    private lateinit var cut: CompanyInfoResponseToRepositoryModelMapperImpl

    @Before
    fun setUp() {
        cut = CompanyInfoResponseToRepositoryModelMapperImpl()
    }

    @Test
    fun `Given companyInfoResponses when toRepositoryModel then returns expected result`() {
        // When
        val actualValue = cut.toRepositoryModel(givenCompanyInfo)

        // Then
        assertEquals(expected, actualValue)
    }
}
