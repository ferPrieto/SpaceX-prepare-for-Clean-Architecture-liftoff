package prieto.fernando.spacex.presentation.vm.model

sealed interface DashboardUiState{
    val isLoading: Boolean
    val isError: Boolean

    data class NoCompanyInfo(
        override val isLoading: Boolean,
        override val isError: Boolean
    ) : DashboardUiState

    data class HasCompanyInfo(
        val companyInfo: String,
        override val isLoading: Boolean,
        override val isError: Boolean
    ) : DashboardUiState
}