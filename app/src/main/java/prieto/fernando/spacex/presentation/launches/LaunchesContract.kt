package prieto.fernando.spacex.presentation.launches

import prieto.fernando.core.presentation.ViewEvent
import prieto.fernando.core.presentation.ViewSideEffect
import prieto.fernando.core.presentation.ViewState
import prieto.fernando.spacex.presentation.LinkType

class LaunchesContract {
    sealed class Event : ViewEvent {
        data class Links(val links: prieto.fernando.spacex.presentation.launches.Links) : Event()
        data class Filter(val year: String, val orderedChecked: Boolean) : Event()
    }

    data class State(
        val launches: List<Launch>,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class ClickableLink : Effect() {
            data class OneLink(val linkType: LinkType, val link: String) : ClickableLink()
            data class TwoLinks(val linkYoutube: String, val linkWikipedia: String) :
                ClickableLink()

            data class Empty(val unit: Unit) : ClickableLink()
        }

        data class LinkClicked(val link: String) : Effect()
        object FilterClicked : Effect()
        data class FilterSelected(val orderChecked: Boolean, val yearValue: String) : Effect()
    }
}

enum class LinkType {
    YOUTUBE,
    WIKIPEDIA
}