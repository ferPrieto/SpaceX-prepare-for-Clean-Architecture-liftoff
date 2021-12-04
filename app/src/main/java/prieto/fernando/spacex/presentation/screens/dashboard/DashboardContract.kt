package prieto.fernando.spacex.presentation.screens.dashboard

import prieto.fernando.core.presentation.ViewEvent
import prieto.fernando.core.presentation.ViewSideEffect
import prieto.fernando.core.presentation.ViewState

class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfoUiModel: CompanyInfoUiModel,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}