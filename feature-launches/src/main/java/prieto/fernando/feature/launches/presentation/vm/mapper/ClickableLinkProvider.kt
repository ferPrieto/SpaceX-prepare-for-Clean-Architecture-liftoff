package prieto.fernando.feature.launches.presentation.vm.mapper

import javax.inject.Inject
import prieto.fernando.feature.launches.presentation.vm.LaunchesContract
import prieto.fernando.feature.launches.presentation.ui.LinksUiModel

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
