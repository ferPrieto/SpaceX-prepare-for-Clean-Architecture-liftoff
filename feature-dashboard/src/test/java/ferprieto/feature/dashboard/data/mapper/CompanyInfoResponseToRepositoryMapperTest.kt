package ferprieto.feature.dashboard.data.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ferprieto.core.network.model.CompanyInfoResponse
import ferprieto.feature.dashboard.data.model.CompanyInfoRepositoryModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CompanyInfoResponseToRepositoryMapperTest(
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
                        2002,
                        7000,
                        1,
                        23
                    ), CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "2002",
                        "7000",
                        1,
                        23
                    )
                ),
                arrayOf(
                    CompanyInfoResponse(
                        "name",
                        "founder",
                        2002,
                        7000,
                        3,
                        27500000000
                    ), CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "2002",
                        "7000",
                        3,
                        27500000000
                    )
                ),
                arrayOf(
                    CompanyInfoResponse(
                        "",
                        "",
                        0,
                        0,
                        0,
                        0
                    ), CompanyInfoRepositoryModel(
                        "",
                        "",
                        "0",
                        "0",
                        0,
                        0
                    )
                )
            )
        }
    }

    private lateinit var cut: CompanyInfoResponseToRepositoryMapperImpl

    @Before
    fun setUp() {
        cut = CompanyInfoResponseToRepositoryMapperImpl()
    }

    @Test
    fun `Given companyInfoResponses when toRepositoryModel then returns expected result`() {
        // When
        val actualValue = cut.toRepositoryModel(givenCompanyInfo)

        // Then
        assertEquals(expected, actualValue)
    }
}

