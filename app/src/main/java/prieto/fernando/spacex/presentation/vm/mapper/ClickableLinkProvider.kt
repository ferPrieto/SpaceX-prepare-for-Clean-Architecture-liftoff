package prieto.fernando.spacex.presentation.vm.mapper

import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import javax.inject.Inject

class ClickableLinkProvider @Inject constructor() {
    fun getClickableLink(linksUiModel: LinksUiModel): LaunchesContract.Effect.ClickableLink =
        when {
            linksUiModel.wikipedia.isNotBlank() && linksUiModel.youTubeLink.isNotBlank() -> {
                LaunchesContract.Effect.ClickableLink.All(
                    linksUiModel.youTubeLink,
                    linksUiModel.wikipedia
                )
            }
            linksUiModel.wikipedia.isNotBlank() && linksUiModel.youTubeLink.isBlank() -> {
                LaunchesContract.Effect.ClickableLink.Wikipedia(linksUiModel.wikipedia)
            }
            linksUiModel.wikipedia.isBlank() && linksUiModel.youTubeLink.isNotBlank() -> {
                LaunchesContract.Effect.ClickableLink.Youtube(linksUiModel.youTubeLink)
            }
            else -> {
                LaunchesContract.Effect.ClickableLink.None
            }
        }
}
