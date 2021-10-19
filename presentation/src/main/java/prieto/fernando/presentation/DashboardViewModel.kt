package prieto.fernando.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import prieto.fernando.core.event.Event
import prieto.fernando.core.event.eventOf
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchUiModel
import timber.log.Timber
import javax.inject.Inject

abstract class DashboardViewModel : ViewModel() {
    abstract fun companyInfo()

    abstract val companyInfo: LiveData<CompanyInfoUiModel>
    abstract val loadingHeader: LiveData<Boolean>
    abstract val headerError: LiveData<Event<Unit>>
}

class DashboardViewModelImpl @Inject constructor(
    private val getCompanyInfo: GetCompanyInfo,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper
) : DashboardViewModel() {

    private val _loadingHeader = MediatorLiveData<Boolean>()
    override val loadingHeader: LiveData<Boolean>
        get() = _loadingHeader

    private val _companyInfo = MediatorLiveData<CompanyInfoUiModel>()
    override val companyInfo: LiveData<CompanyInfoUiModel>
        get() = _companyInfo

    private val _headerError = MediatorLiveData<Event<Unit>>()
    override val headerError: LiveData<Event<Unit>>
        get() = _headerError

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loadingHeader.value = false
    }

    override fun companyInfo() {
        viewModelScope.launch(errorHandler) {
            _loadingHeader.value = true
            getCompanyInfo.execute()
                .catch { throwable ->
                    Timber.e(throwable)
                    _headerError.postValue(eventOf(Unit))
                    _loadingHeader.value = false
                }
                .collect { companyInfoDomainModel ->
                    val companyInfoUiModel =
                        companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)
                    _companyInfo.postValue(companyInfoUiModel)
                    _loadingHeader.value = false
                }
        }
    }
}