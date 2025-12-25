package prieto.fernando.feature.dashboard.presentation.vm

import prieto.fernando.feature.dashboard.presentation.base.ViewEvent
import prieto.fernando.feature.dashboard.presentation.base.ViewSideEffect
import prieto.fernando.feature.dashboard.presentation.base.ViewState
import prieto.fernando.feature.dashboard.presentation.ui.CompanyInfoUiModel

class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfoUiModel: CompanyInfoUiModel,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}

