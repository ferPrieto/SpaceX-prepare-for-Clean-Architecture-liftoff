package prieto.fernando.presentation.transformer

import com.nhaarman.mockito_kotlin.given
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.presentation.mapper.DateTimeProvider
import prieto.fernando.presentation.mapper.DateTransformerImpl
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class DateTransformerImplTest {
    private lateinit var cut: DateTransformerImpl

    @Mock
    lateinit var dateTimeProvider: DateTimeProvider

    @Before
    fun setUp() {
        cut = DateTransformerImpl(dateTimeProvider)
    }

    @Test
    fun `Given date when dateToDateString then returns expected result`() {
        // Given
        val dateTime = DateTime(2019, 1, 2, 4, 3)
        val expected = "02-01-2019 at 04:03"

        // When
        val actualValue = cut.dateToDateString(dateTime)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given date when getDifferenceOfDays then returns expected result`() {
        // Given
        val dateTime = DateTime(2022, 1, 2, 4, 3)
        given { dateTimeProvider.today() }.willReturn(DateTime(2019, 1, 2, 4, 3))
        val expected = "1096"

        // When
        val actualValue = cut.getDifferenceOfDays(dateTime)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given date when isPast then returns expected result`() {
        // Given
        val dateTime = DateTime(2018, 1, 2, 4, 3)
        given { dateTimeProvider.today() }.willReturn(DateTime(2019, 1, 2, 4, 3))
        val expected = true

        // When
        val actualValue = cut.isPast(dateTime)

        // Then
        assertEquals(expected, actualValue)
    }
}
