package prieto.fernando.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.core.event.Event
import prieto.fernando.core.event.eventOf
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.model.CompanyInfoUiModel
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
            _loadingCompanyInfo.value = true
            try {
                getCompanyInfo.execute()
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { companyInfoDomainModel ->
                        val companyInfoUiModel =
                            companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)
                        _companyInfo.postValue(companyInfoUiModel)
                        _loadingCompanyInfo.value = false
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
        _companyInfoError.postValue(eventOf(Unit))
        _loadingCompanyInfo.value = false
    }
}
