package prieto.fernando.spacex.presentation.vm.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class ClickableLinkProviderTest(
    private val linksUiModel: LinksUiModel,
    private val expected: LaunchesContract.Effect.ClickableLink
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    LinksUiModel(
                        missionPatchSmall = "",
                        wikipedia = "someWikipedia URL",
                        youTubeLink = "someYoutube URL"
                    ),
                    LaunchesContract.Effect.ClickableLink.All(
                        wikipedia = "someWikipedia URL",
                        youTubeLink = "someYoutube URL"
                    )
                ),
                arrayOf(
                    LinksUiModel(
                        missionPatchSmall = "",
                        wikipedia = "someWikipedia URL",
                        youTubeLink = " "
                    ),
                    LaunchesContract.Effect.ClickableLink.Wikipedia(
                        wikipedia = "someWikipedia URL"
                    )
                ),
                arrayOf(
                    LinksUiModel(
                        missionPatchSmall = "",
                        wikipedia = "",
                        youTubeLink = "someYoutube URL"
                    ),
                    LaunchesContract.Effect.ClickableLink.Youtube(
                        youTubeLink = "someYoutube URL"
                    )
                ),
                arrayOf(
                    LinksUiModel(
                        missionPatchSmall = "",
                        wikipedia = " ",
                        youTubeLink = " "
                    ),
                    LaunchesContract.Effect.ClickableLink.None
                ),
                arrayOf(
                    LinksUiModel(
                        missionPatchSmall = "",
                        wikipedia = "",
                        youTubeLink = ""
                    ),
                    LaunchesContract.Effect.ClickableLink.None
                )
            )
        }
    }

    private lateinit var cut: ClickableLinkProvider

    @Before
    fun setUp() {
        cut = ClickableLinkProvider()
    }

    @Test
    fun `Given linksUiModel when getClickableLink then returns expected result`() {
        // When
        val actualValue = cut.getClickableLink(linksUiModel)

        // Then
        assertEquals(expected, actualValue)
    }
}
