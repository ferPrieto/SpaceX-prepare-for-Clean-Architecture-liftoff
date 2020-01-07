package prieto.fernando.presentation.mapper

import com.nhaarman.mockito_kotlin.given
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.presentation.model.LinksUiModel
import prieto.fernando.presentation.model.RocketUiModel
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class LaunchesDomainToUiModelMapperTest {

    private lateinit var cut: LaunchesDomainToUiModelMapperImpl

    @Mock
    lateinit var dateTransformer: DateTransformer

    @get:Rule
    var rule: MethodRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        cut = LaunchesDomainToUiModelMapperImpl(dateTransformer)
    }

    @Test
    fun `Given 2 launches when toUiModel then return expected result`() {
        // Given
        val launches = listOf(
            LaunchDomainModel(
                "missionName",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2020-12-07T12:00:00.000Z"),
                RocketDomainModel("rocketName2", "rocketType2"),
                LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2"),
                false
            )
        )

        val expected = listOf(
            LaunchUiModel(
                "missionName",
                "11-12-2019 at 12:00",
                true,
                "0",
                RocketUiModel("rocketName", "rocketType"),
                LinksUiModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchUiModel(
                "missionName2",
                "07-12-2020 at 12:00",
                false,
                "361",
                RocketUiModel("rocketName2", "rocketType2"),
                LinksUiModel("patchLink2", "wikipediaLink2", "videoLink2"),
                false
            )
        )

        given { dateTransformer.dateToDateString(buildDate("2019-12-11T12:00:00.000Z")) }
            .willReturn("11-12-2019 at 12:00")
        given { dateTransformer.dateToDateString(buildDate("2020-12-07T12:00:00.000Z")) }
            .willReturn("07-12-2020 at 12:00")

        given { dateTransformer.getDifferenceOfDays(buildDate("2019-12-11T12:00:00.000Z")) }
            .willReturn("0")
        given { dateTransformer.getDifferenceOfDays(buildDate("2020-12-07T12:00:00.000Z")) }
            .willReturn("361")

        given { dateTransformer.isPast(buildDate("2019-12-11T12:00:00.000Z")) }
            .willReturn(true)
        given { dateTransformer.isPast(buildDate("2020-12-07T12:00:00.000Z")) }
            .willReturn(false)

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)

    }

    @Test
    fun `Given launch when toUiModel then return expected result`() {
        // Given
        val launches = listOf(
            LaunchDomainModel(
                "missionName",
                buildDate("2019-12-13T13:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val expected = listOf(
            LaunchUiModel(
                "missionName",
                "13-12-2019 at 13:00",
                false,
                "1",
                RocketUiModel("rocketName", "rocketType"),
                LinksUiModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )

        given { dateTransformer.dateToDateString(buildDate("2019-12-13T13:00:00.000Z")) }
            .willReturn("13-12-2019 at 13:00")
        given { dateTransformer.getDifferenceOfDays(buildDate("2019-12-13T13:00:00.000Z")) }
            .willReturn("1")

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given no launches when toUiModel then return expected result`() {
        // Given
        val launches = emptyList<LaunchDomainModel>()
        val expected = emptyList<LaunchUiModel>()

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)
    }

    private fun buildDate(dateValue: String) =
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .parseDateTime(dateValue.replace("Z", "+0000"))
}
