package prieto.fernando.feature.launches.presentation.vm

import prieto.fernando.feature.launches.presentation.base.ViewEvent
import prieto.fernando.feature.launches.presentation.base.ViewSideEffect
import prieto.fernando.feature.launches.presentation.base.ViewState
import prieto.fernando.feature.launches.presentation.ui.LaunchUiModel
import prieto.fernando.feature.launches.presentation.ui.LinksUiModel

const val LAUNCH_LISTEN_FOR_EFFECTS = "launch-listen-to-effects"

class LaunchesContract {
    sealed class Event : ViewEvent {
        data class LinkClicked(val link: String) : Event()
        data class ClickableLinks(val linksUiModel: LinksUiModel) : Event()
        data class Filter(val year: String, val orderedChecked: Boolean) : Event()
    }

    data class State(
        val launchUiModels: List<LaunchUiModel>,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class ClickableLink : Effect() {
            data class All(val youTubeLink: String, val wikipedia: String) : ClickableLink()
            data class Youtube(val youTubeLink: String) : ClickableLink()
            data class Wikipedia(val wikipedia: String) : ClickableLink()
            object None : ClickableLink()
        }

        data class LinkClicked(val link: String) : Effect()
    }
}
