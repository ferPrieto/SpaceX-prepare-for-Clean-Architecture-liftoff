package prieto.fernando.spacex.ui.vm

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
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.ui.vm.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.spacex.ui.vm.model.LaunchUiModel
import timber.log.Timber
import javax.inject.Inject

abstract class LaunchesViewModel : ViewModel() {
    abstract fun launches(yearFilterCriteria: Int = -1, ascendantOrder: Boolean = false)
    abstract fun openLink(link: String)
    abstract fun onFilterClicked()

    abstract val launches: LiveData<List<LaunchUiModel>>
    abstract val loadingLaunches: LiveData<Boolean>
    abstract val launchesError: LiveData<Event<Unit>>
    abstract val openLink: LiveData<Event<String>>
    abstract val showDialog: LiveData<Event<Unit>>
}

class LaunchesViewModelImpl @Inject constructor(
    private val getLaunches: GetLaunches,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
) : LaunchesViewModel() {

    private val _openLink = MediatorLiveData<Event<String>>()
    private val _showDialog = MediatorLiveData<Event<Unit>>()

    override val openLink: LiveData<Event<String>>
        get() = _openLink
    override val showDialog: LiveData<Event<Unit>>
        get() = _showDialog

    private val _loadingLaunches = MediatorLiveData<Boolean>()
    override val loadingLaunches: LiveData<Boolean>
        get() = _loadingLaunches

    private val _launches = MediatorLiveData<List<LaunchUiModel>>()
    override val launches: LiveData<List<LaunchUiModel>>
        get() = _launches

    private val _launchesError = MediatorLiveData<Event<Unit>>()
    override val launchesError: LiveData<Event<Unit>>
        get() = _launchesError


    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loadingLaunches.value = false
    }

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        viewModelScope.launch(errorHandler) {
            _loadingLaunches.value = true
            try {
                getLaunches.execute(yearFilterCriteria, ascendantOrder)
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { launchesDomainModel ->
                        val launchesUiModel =
                            launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)
                        _launches.postValue(launchesUiModel)
                        _loadingLaunches.value = false
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
        _launchesError.postValue(eventOf(Unit))
        _loadingLaunches.value = false
    }

    override fun openLink(link: String) {
        viewModelScope.launch {
            _openLink.postValue(eventOf(link))
        }
    }

    override fun onFilterClicked() {
        viewModelScope.launch {
            _showDialog.postValue(eventOf(Unit))
        }
    }
}