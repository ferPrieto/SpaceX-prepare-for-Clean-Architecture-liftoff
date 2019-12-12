package prieto.fernando.data_api.mapper

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class DateFormatterImplTest(
    private val givenDateValue: String,
    private val expected: DateTime
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("2020-12-11T00:00:00.000Z", buildDate("11-12-2020")),
                arrayOf("2021-01-01T00:00:00.000Z", buildDate("01-01-2021")),
                arrayOf("1990-03-01T00:00:00.000Z", buildDate("01-03-1990")),
                arrayOf("2001-01-01T00:00:00.000Z", buildDate("01-01-2001")),
                arrayOf("2015-01-10T00:00:00.000Z", buildDate("10-01-2015"))
            )
        }

        private fun buildDate(dateValue: String) =
            DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(dateValue)
    }

    private lateinit var cut: DateFormatterImpl

    @Before
    fun setUp() {
        cut = DateFormatterImpl()
    }

    @Test
    fun `Given dateValue when format then returns expected result`() {
        // When
        val actualValue = cut.format(givenDateValue)

        // Then
        assertEquals(expected, actualValue)
    }
}
