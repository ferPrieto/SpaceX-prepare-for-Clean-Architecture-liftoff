package prieto.fernando.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

abstract class MainViewModel @ViewModelInject constructor() : ViewModel() {
    abstract fun launches(yearFilterCriteria: Int = -1, ascendantOrder: Boolean = false)
    abstract fun companyInfo()
    abstract fun openLink(link: String)
    abstract fun onFilterClicked()

    abstract val companyInfo: LiveData<CompanyInfoUiModel>
    abstract val launches: LiveData<List<LaunchUiModel>>
    abstract val loadingHeader: LiveData<Boolean>
    abstract val loadingBody: LiveData<Boolean>
    abstract val headerError: LiveData<Event<Unit>>
    abstract val bodyError: LiveData<Event<Unit>>
    abstract val openLink: LiveData<Event<String>>
    abstract val showDialog: LiveData<Event<Unit>>
}

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModelImpl @Inject constructor(
    private val getLaunches: GetLaunches,
    private val getCompanyInfo: GetCompanyInfo,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
) : MainViewModel() {
    private val openLinkChannel = ConflatedBroadcastChannel<Event<String>>()
    private val showDialogChannel = ConflatedBroadcastChannel<Event<Unit>>()

    override val openLink: LiveData<Event<String>> = openLinkChannel.asFlow().asLiveData()
    override val showDialog: LiveData<Event<Unit>> = showDialogChannel.asFlow().asLiveData()

    private val _loadingHeader = MediatorLiveData<Boolean>()
    override val loadingHeader: LiveData<Boolean>
        get() = _loadingHeader

    private val _loadingBody = MediatorLiveData<Boolean>()
    override val loadingBody: LiveData<Boolean>
        get() = _loadingBody

    private val _companyInfo = MediatorLiveData<CompanyInfoUiModel>()
    override val companyInfo: LiveData<CompanyInfoUiModel>
        get() = _companyInfo

    private val _launches = MediatorLiveData<List<LaunchUiModel>>()
    override val launches: LiveData<List<LaunchUiModel>>
        get() = _launches

    private val _bodyError = MediatorLiveData<Event<Unit>>()
    override val bodyError: LiveData<Event<Unit>>
        get() = _bodyError

    private val _headerError = MediatorLiveData<Event<Unit>>()
    override val headerError: LiveData<Event<Unit>>
        get() = _headerError

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loadingBody.value = false
        _loadingHeader.value = false
    }

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        viewModelScope.launch(errorHandler) {
            _loadingBody.value = true
            getLaunches.execute(yearFilterCriteria, ascendantOrder)
                .catch { throwable ->
                    Timber.e(throwable)
                    _bodyError.postValue(eventOf(Unit))
                    _loadingBody.value = false
                }
                .collect { launchesDomainModel ->
                    val launchesUiModel =
                        launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)
                    _launches.postValue(launchesUiModel)
                    _loadingBody.value = false
                }
        }
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

    override fun openLink(link: String) {
        openLinkChannel.offer(eventOf(link))
    }

    override fun onFilterClicked() {
        showDialogChannel.offer(eventOf(Unit))
    }
}