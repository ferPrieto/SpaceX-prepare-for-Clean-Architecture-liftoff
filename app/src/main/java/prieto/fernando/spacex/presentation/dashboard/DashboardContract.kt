package prieto.fernando.spacex.presentation.dashboard

import prieto.fernando.core.presentation.ViewEvent
import prieto.fernando.core.presentation.ViewState


class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfo: CompanyInfo,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState
}