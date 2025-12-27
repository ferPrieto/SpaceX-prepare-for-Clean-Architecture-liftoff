package ferprieto.feature.launches.data.mapper

import io.mockk.impl.annotations.MockK
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ferprieto.core.network.mapper.DateFormatter
import ferprieto.core.network.mapper.DateFormatterImpl
import ferprieto.core.network.model.LaunchesResponse
import ferprieto.core.network.model.LinksResponse
import ferprieto.core.network.model.RocketResponse
import ferprieto.feature.launches.data.model.LaunchRepositoryModel
import ferprieto.feature.launches.data.model.LinksRepositoryModel
import ferprieto.feature.launches.data.model.RocketRepositoryModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class LaunchesResponseToRepositoryMapperTest(
    private val givenLaunches: List<LaunchesResponse>,
    private val expected: List<LaunchRepositoryModel>
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<List<Any>>> {
            return listOf(
                arrayOf(
                    listOf(
                        LaunchesResponse(
                            flightNumber = 1,
                            missionName = "missionName",
                            missionId = null,
                            launchYear = "2020",
                            launchDateUnix = 1607644800L,
                            launchDateUtc = "2020-12-11T00:00:00.000Z",
                            launchDateLocal = "2020-12-11T00:00:00-05:00",
                            isTentative = false,
                            tentativeMaxPrecision = "hour",
                            tbd = false,
                            launchWindow = 0,
                            rocket = RocketResponse(
                                rocketId = "falcon9",
                                rocketName = "rocketName",
                                rocketType = "rocketType",
                                firstStage = null,
                                secondStage = null,
                                fairings = null
                            ),
                            ships = null,
                            telemetry = null,
                            launchSite = null,
                            launchSuccess = false,
                            launchFailureDetails = null,
                            links = LinksResponse(
                                missionPatch = "patchLink",
                                missionPatchSmall = "patchLink",
                                redditCampaign = null,
                                redditLaunch = null,
                                redditRecovery = null,
                                redditMedia = null,
                                presskit = null,
                                articleLink = null,
                                wikipedia = "wikipediaLink",
                                videoLink = "videoLink",
                                youtubeId = null,
                                flickrImages = null
                            ),
                            details = null,
                            upcoming = false,
                            staticFireDateUtc = null,
                            staticFireDateUnix = null,
                            timeline = null,
                            crew = null
                        ),
                        LaunchesResponse(
                            flightNumber = 2,
                            missionName = "missionName2",
                            missionId = null,
                            launchYear = "2021",
                            launchDateUnix = 1609459200L,
                            launchDateUtc = "2021-01-01T00:00:00.000Z",
                            launchDateLocal = "2021-01-01T00:00:00-05:00",
                            isTentative = false,
                            tentativeMaxPrecision = "hour",
                            tbd = false,
                            launchWindow = 0,
                            rocket = RocketResponse(
                                rocketId = "falcon9",
                                rocketName = "rocketName2",
                                rocketType = "rocketType2",
                                firstStage = null,
                                secondStage = null,
                                fairings = null
                            ),
                            ships = null,
                            telemetry = null,
                            launchSite = null,
                            launchSuccess = false,
                            launchFailureDetails = null,
                            links = LinksResponse(
                                missionPatch = "patchLink2",
                                missionPatchSmall = "patchLink2",
                                redditCampaign = null,
                                redditLaunch = null,
                                redditRecovery = null,
                                redditMedia = null,
                                presskit = null,
                                articleLink = null,
                                wikipedia = "wikipediaLink2",
                                videoLink = "videoLink2",
                                youtubeId = null,
                                flickrImages = null
                            ),
                            details = null,
                            upcoming = false,
                            staticFireDateUtc = null,
                            staticFireDateUnix = null,
                            timeline = null,
                            crew = null
                        )
                    ),
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("11-12-2020"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel("patchLink", "wikipediaLink", "videoLink"),
                            false
                        ),
                        LaunchRepositoryModel(
                            "missionName2",
                            buildDate("01-01-2021"),
                            RocketRepositoryModel("rocketName2", "rocketType2"),
                            LinksRepositoryModel("patchLink2", "wikipediaLink2", "videoLink2"),
                            false
                        )
                    )
                ),
                arrayOf(
                    listOf(
                        LaunchesResponse(
                            flightNumber = 1,
                            missionName = "missionName",
                            missionId = null,
                            launchYear = "1990",
                            launchDateUnix = 636249600L,
                            launchDateUtc = "1990-03-01T00:00:00.000Z",
                            launchDateLocal = "1990-03-01T00:00:00-05:00",
                            isTentative = false,
                            tentativeMaxPrecision = "hour",
                            tbd = false,
                            launchWindow = 0,
                            rocket = RocketResponse(
                                rocketId = "falcon9",
                                rocketName = "rocketName",
                                rocketType = "rocketType",
                                firstStage = null,
                                secondStage = null,
                                fairings = null
                            ),
                            ships = null,
                            telemetry = null,
                            launchSite = null,
                            launchSuccess = true,
                            launchFailureDetails = null,
                            links = LinksResponse(
                                missionPatch = null,
                                missionPatchSmall = null,
                                redditCampaign = null,
                                redditLaunch = null,
                                redditRecovery = null,
                                redditMedia = null,
                                presskit = null,
                                articleLink = null,
                                wikipedia = null,
                                videoLink = null,
                                youtubeId = null,
                                flickrImages = null
                            ),
                            details = null,
                            upcoming = false,
                            staticFireDateUtc = null,
                            staticFireDateUnix = null,
                            timeline = null,
                            crew = null
                        )
                    ),
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("01-03-1990"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel(
                                "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png",
                                "",
                                ""
                            ),
                            true
                        )
                    )
                ),
                arrayOf(emptyList(), emptyList())
            )
        }

        private fun buildDate(dateValue: String) =
            DateTimeFormat.forPattern("dd-MM-yyyy").withZoneUTC().parseDateTime(dateValue)
    }

    private lateinit var cut: LaunchesResponseToRepositoryMapperImpl

    @MockK
    lateinit var dateFormatter: DateFormatter

    @Before
    fun setUp() {
        dateFormatter = DateFormatterImpl()
        cut = LaunchesResponseToRepositoryMapperImpl(dateFormatter)
    }

    @Test
    fun `Given launchResponseModels when toRepositoryModel then returns expected result`() {
        // When
        val actualValue = cut.toRepositoryModel(givenLaunches)

        // Then
        assertEquals(expected, actualValue)
    }
}

