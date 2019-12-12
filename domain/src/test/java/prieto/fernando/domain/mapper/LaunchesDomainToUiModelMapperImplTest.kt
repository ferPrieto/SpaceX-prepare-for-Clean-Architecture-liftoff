package prieto.fernando.domain.mapper

import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.presentation.model.LinksUiModel
import prieto.fernando.presentation.model.RocketUiModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class LaunchesDomainToUiModelMapperImplTest(
    private val givenLaunches: List<LaunchDomainModel>,
    private val expected: List<LaunchUiModel>
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<List<Any>>> {
            return listOf(
                arrayOf(
                    listOf(
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
                    ,
                    listOf(
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
                ),
                arrayOf(
                    listOf(
                        LaunchDomainModel(
                            "missionName",
                            buildDate("2019-12-13T13:00:00.000Z"),
                            RocketDomainModel("rocketName", "rocketType"),
                            LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                            true
                        )
                    ),
                    listOf(
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
                ),
                arrayOf(emptyList(), emptyList())
            )
        }

        private fun buildDate(dateValue: String) =
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parseDateTime(dateValue.replace("Z", "+0000"))
    }

    private lateinit var cut: LaunchesDomainToUiModelMapperImpl

    @Mock
    lateinit var dateTransformer: DateTransformer

    @Before
    fun setUp() {
        dateTransformer = DateTransformerImpl(DateTimeProvider())
        cut = LaunchesDomainToUiModelMapperImpl(dateTransformer)
    }

    @Test
    fun `Given launchDomainModels when toUiModel then returns expected result`() {
        // When
        val actualValue = cut.toUiModel(givenLaunches)

        // Then
        assertEquals(expected, actualValue)
    }
}
