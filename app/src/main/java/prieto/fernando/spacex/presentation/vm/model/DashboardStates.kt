package prieto.fernando.spacex.presentation.vm.model

sealed class DashboardStates<out T> {
    object Loading : DashboardStates<Nothing>()
    class Data<out T>(val data: T) : DashboardStates<T>()
    class Error(val error: Throwable) : DashboardStates<Nothing>()
}