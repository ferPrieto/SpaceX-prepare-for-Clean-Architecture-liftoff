package ferprieto.feature.dashboard.presentation.vm

import ferprieto.feature.dashboard.presentation.base.ViewEvent
import ferprieto.feature.dashboard.presentation.base.ViewSideEffect
import ferprieto.feature.dashboard.presentation.base.ViewState
import ferprieto.feature.dashboard.presentation.ui.CompanyInfoUiModel

class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfoUiModel: CompanyInfoUiModel,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}

