package prieto.fernando.domain.mapper

import org.junit.Before
import org.junit.Test
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import kotlin.test.assertEquals

class LaunchesDomainFilterImplTest {
    private lateinit var cut: LaunchesDomainFilterImpl

    @Before
    fun setUp() {
        cut = LaunchesDomainFilterImpl()
    }

    @Test
    fun `Given launches and filter Descendant flag when filter then return expected result`() {
        // Given
        val launchesDomainModel = listOf(
            LaunchDomainModel(
                "missionName0",
                buildDate("2019-12-12T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchDomainModel(
                "missionName1",
                buildDate("2018-12-16T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val expected = listOf(
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val filterYear = 2019
        val ascendantOrder = false

        // When
        val actualValue = cut.filter(launchesDomainModel, filterYear, ascendantOrder)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given launches and filter Ascendant flag when filter then return expected result`() {
        // Given
        val launchesDomainModel = listOf(
            LaunchDomainModel(
                "missionName0",
                buildDate("2019-12-12T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchDomainModel(
                "missionName1",
                buildDate("2018-12-16T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val expected = listOf(
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val filterYear = 2019
        val ascendantOrder = true

        // When
        val actualValue = cut.filter(launchesDomainModel, filterYear, ascendantOrder)

        // Then
        assertEquals(expected, actualValue)
    }
}

fun buildDate(dateValue: String): DateTime =
    DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime(dateValue.replace("Z", "+0000"))
