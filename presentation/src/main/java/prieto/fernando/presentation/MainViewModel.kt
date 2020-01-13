package prieto.fernando.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
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

abstract class MainViewModel : ViewModel() {
    abstract fun launches(yearFilterCriteria: Int = -1, ascendantOrder: Boolean = false)
    abstract fun companyInfo()
    abstract fun openLink(link: String)
    abstract fun onFilterClicked()

    abstract val companyInfo: LiveData<CompanyInfoUiModel>
    abstract val launches: LiveData<List<LaunchUiModel>>
    abstract val loadingBody: LiveData<Boolean>
    abstract val loadingHeader: LiveData<Boolean>
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
    private val companyInfoChannel = ConflatedBroadcastChannel<CompanyInfoUiModel>()
    private val launchesChannel = ConflatedBroadcastChannel<List<LaunchUiModel>>()
    private val loadingBodyChannel = ConflatedBroadcastChannel(false)
    private val loadingHeaderChannel = ConflatedBroadcastChannel(false)
    private val openLinkChannel = ConflatedBroadcastChannel<Event<String>>()
    private val showDialogChannel = ConflatedBroadcastChannel<Event<Unit>>()
    private val headerErrorChannel = ConflatedBroadcastChannel<Event<Unit>>()
    private val bodyErrorChannel = ConflatedBroadcastChannel<Event<Unit>>()

    override val companyInfo: LiveData<CompanyInfoUiModel> =
        companyInfoChannel.asFlow().asLiveData()
    override val launches: LiveData<List<LaunchUiModel>> = launchesChannel.asFlow().asLiveData()
    override val loadingHeader: LiveData<Boolean> = loadingHeaderChannel.asFlow().asLiveData()
    override val loadingBody: LiveData<Boolean> = loadingBodyChannel.asFlow().asLiveData()
    override val openLink: LiveData<Event<String>> = openLinkChannel.asFlow().asLiveData()
    override val showDialog: LiveData<Event<Unit>> = showDialogChannel.asFlow().asLiveData()
    override val headerError: LiveData<Event<Unit>> = headerErrorChannel.asFlow().asLiveData()
    override val bodyError: LiveData<Event<Unit>> = bodyErrorChannel.asFlow().asLiveData()

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        viewModelScope.launch {
            loadingBody {
                getLaunches.execute(yearFilterCriteria, ascendantOrder)
                    .onSuccess { launchesDomainModel ->
                        val launchesUiModel =
                            launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)
                        launchesChannel.offer(launchesUiModel)
                    }.onFailure { throwable ->
                        Timber.e(throwable)
                        bodyErrorChannel.offer(eventOf(Unit))
                    }
            }
        }
    }

    override fun companyInfo() {
        viewModelScope.launch {
            loadingHeader {
                getCompanyInfo.execute()
                    .onSuccess { companyInfoDomainModel ->
                        val companyInfo =
                            companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)
                        companyInfoChannel.offer(companyInfo)
                    }.onFailure { throwable ->
                        Timber.e(throwable)
                        headerErrorChannel.offer(eventOf(Unit))
                    }
            }
        }
    }

    override fun openLink(link: String) {
        openLinkChannel.offer(eventOf(link))
    }

    override fun onFilterClicked() {
        showDialogChannel.offer(eventOf(Unit))
    }

    private inline fun <T> loadingBody(function: () -> T): T {
        loadingBodyChannel.offer(true)
        val result = function()
        loadingBodyChannel.offer(false)
        return result
    }

    private inline fun <T> loadingHeader(function: () -> T): T {
        loadingHeaderChannel.offer(true)
        val result = function()
        loadingHeaderChannel.offer(false)
        return result
    }
}