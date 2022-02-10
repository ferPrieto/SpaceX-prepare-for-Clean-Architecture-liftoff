package prieto.fernando.spacex.presentation.screens.dashboard

import prieto.fernando.spacex.presentation.vm.base.ViewEvent
import prieto.fernando.spacex.presentation.vm.base.ViewSideEffect
import prieto.fernando.spacex.presentation.vm.base.ViewState

class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfoUiModel: CompanyInfoUiModel,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}
