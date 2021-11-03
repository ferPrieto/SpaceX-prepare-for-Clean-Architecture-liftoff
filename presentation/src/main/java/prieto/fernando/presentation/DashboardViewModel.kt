package prieto.fernando.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import prieto.fernando.core.event.Event
import prieto.fernando.core.event.eventOf
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.state.DashboardUiState
import timber.log.Timber
import javax.inject.Inject

abstract class DashboardViewModel : ViewModel() {
    abstract fun companyInfo()

    abstract val companyInfo: LiveData<CompanyInfoUiModel>
    abstract val loadingCompanyInfo: LiveData<Boolean>
    abstract val companyInfoError: LiveData<Event<Unit>>
}

class DashboardViewModelImpl @Inject constructor(
    private val getCompanyInfo: GetCompanyInfo,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper
) : DashboardViewModel() {

    private val viewModelState = MutableStateFlow(DashboardViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val _loadingCompanyInfo = MediatorLiveData<Boolean>()
    override val loadingCompanyInfo: LiveData<Boolean>
        get() = _loadingCompanyInfo

    private val _companyInfo = MediatorLiveData<CompanyInfoUiModel>()
    override val companyInfo: LiveData<CompanyInfoUiModel>
        get() = _companyInfo

    private val _companyInfoError = MediatorLiveData<Event<Unit>>()
    override val companyInfoError: LiveData<Event<Unit>>
        get() = _companyInfoError

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loadingCompanyInfo.value = false
    }

    override fun companyInfo() {
        viewModelScope.launch(errorHandler) {
            viewModelState.update { it.copy(isLoading = true) }
            try {
                getCompanyInfo.execute()
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { companyInfoDomainModel ->
                        val companyInfoUiModel =
                            companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)
                       // _companyInfo.postValue(companyInfoUiModel)
                        viewModelState.update { it.copy(isLoading = false, companyInfo = "companyInfo") }
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
   /*     _companyInfoError.postValue(eventOf(Unit))
        _loadingCompanyInfo.value = false*/
        viewModelState.update { it.copy(isError = true, companyInfo = null) }
    }
}

private data class DashboardViewModelState(
    val companyInfo: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
) {
    fun toUiState(): DashboardUiState =
        if (companyInfo == null) {
            DashboardUiState.NoCompanyInfo(
                isLoading = isLoading,
                isError = isError
            )
        } else {
            DashboardUiState.HasCompanyInfo(
                companyInfo = companyInfo,
                isLoading = isLoading,
                isError = isError
            )
        }
}
