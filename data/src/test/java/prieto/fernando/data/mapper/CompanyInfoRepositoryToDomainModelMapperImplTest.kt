package prieto.fernando.data.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.domain.model.CompanyInfoDomainModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CompanyInfoRepositoryToDomainModelMapperImplTest(
    private val givenCompanyInfo: CompanyInfoRepositoryModel,
    private val expected: CompanyInfoDomainModel
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    ), CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    )
                ),
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    ), CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    )
                ),
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    ), CompanyInfoDomainModel(
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

    private lateinit var cut: CompanyInfoRepositoryToDomainModelMapperImpl

    @Before
    fun setUp() {
        cut = CompanyInfoRepositoryToDomainModelMapperImpl()
    }

    @Test
    fun `Given companyInfos when toDomainModel then returns expected result`() {
        // When
        val actualValue = cut.toDomainModel(givenCompanyInfo)

        // Then
        assertEquals(expected, actualValue)
    }
}
