package prieto.fernando.spacex.presentation.vm.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import prieto.fernando.core_android_test.util.buildDate
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import prieto.fernando.spacex.presentation.screens.launches.LaunchUiModel
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import prieto.fernando.spacex.presentation.screens.launches.RocketUiModel
import javax.inject.Inject
import kotlin.test.assertEquals

class LaunchesDomainToUiModelMapperTest {

    private lateinit var cut: LaunchesDomainToUiModelMapperImpl

    @Inject
    lateinit var dateTransformer: DateTransformer

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dateTransformer = mockk()
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
                LinksUiModel(
                    "patchLink",
                    "wikipediaLink",
                    "videoLink"
                ),
                false
            ),
            LaunchUiModel(
                "missionName2",
                "07-12-2020 at 12:00",
                false,
                "361",
                RocketUiModel("rocketName2", "rocketType2"),
                LinksUiModel(
                    "patchLink2",
                    "wikipediaLink2",
                    "videoLink2"
                ),
                false
            )
        )

        every { dateTransformer.dateToDateString(buildDate("2019-12-11T12:00:00.000Z")) }
            .returns("11-12-2019 at 12:00")
        every { dateTransformer.dateToDateString(buildDate("2020-12-07T12:00:00.000Z")) }
            .returns("07-12-2020 at 12:00")

        every { dateTransformer.getDifferenceOfDays(buildDate("2019-12-11T12:00:00.000Z")) }
            .returns("0")
        every { dateTransformer.getDifferenceOfDays(buildDate("2020-12-07T12:00:00.000Z")) }
            .returns("361")

        every { dateTransformer.isPast(buildDate("2019-12-11T12:00:00.000Z")) }
            .returns(true)
        every { dateTransformer.isPast(buildDate("2020-12-07T12:00:00.000Z")) }
            .returns(false)

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
                LinksUiModel(
                    "patchLink",
                    "wikipediaLink",
                    "videoLink"
                ),
                true
            )
        )

        every { dateTransformer.dateToDateString(buildDate("2019-12-13T13:00:00.000Z")) }
            .returns("13-12-2019 at 13:00")
        every { dateTransformer.getDifferenceOfDays(buildDate("2019-12-13T13:00:00.000Z")) }
            .returns("1")
        every { dateTransformer.isPast(buildDate("2019-12-13T13:00:00.000Z")) }.returns(false)

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
}
