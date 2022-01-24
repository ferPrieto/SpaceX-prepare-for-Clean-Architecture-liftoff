package prieto.fernando.spacex.presentation.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.spacex.presentation.screens.dashboard.CompanyInfoUiModel
import prieto.fernando.spacex.presentation.screens.dashboard.DashboardContract
import prieto.fernando.spacex.presentation.vm.base.BaseViewModel
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCompanyInfo: GetCompanyInfo,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper
) : BaseViewModel<DashboardContract.Event, DashboardContract.State, DashboardContract.Effect>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }

    init {
        companyInfo()
    }

    override fun setInitialState(): DashboardContract.State =
        DashboardContract.State(
            companyInfoUiModel = CompanyInfoUiModel("", "", "", "", -1, -1L),
            isLoading = true,
            isError = false
        )

    override fun handleEvents(event: DashboardContract.Event) {}

    fun companyInfo() {
        viewModelScope.launch(errorHandler) {
            try {
                getCompanyInfo.execute()
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { companyInfoDomainModel ->
                        companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)
                            .let { companyInfo ->
                                setState {
                                    copy(
                                        companyInfoUiModel = companyInfo,
                                        isLoading = false
                                    )
                                }
                            }
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }
}
