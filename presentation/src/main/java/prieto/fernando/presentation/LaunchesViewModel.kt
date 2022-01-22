package prieto.fernando.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.core.event.Event
import prieto.fernando.core.event.eventOf
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.presentation.model.LaunchUiModel
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

    override val launches = MutableLiveData<List<LaunchUiModel>>()
    override val loadingLaunches = MutableLiveData<Boolean>()
    override val launchesError = MutableLiveData<Event<Unit>>()
    override val openLink = MutableLiveData<Event<String>>()
    override val showDialog = MutableLiveData<Event<Unit>>()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        loadingLaunches.value = false
    }

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        viewModelScope.launch(errorHandler) {
            loadingLaunches.value = true
            try {
                getLaunches.execute(yearFilterCriteria, ascendantOrder)
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { launchesDomainModel ->
                        val launchesUiModel =
                            launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)
                        launches.postValue(launchesUiModel)
                        loadingLaunches.value = false
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
        launchesError.postValue(eventOf(Unit))
        loadingLaunches.value = false
    }

    override fun openLink(link: String) {
        viewModelScope.launch {
            openLink.postValue(eventOf(link))
        }
    }

    override fun onFilterClicked() {
        viewModelScope.launch {
            showDialog.postValue(eventOf(Unit))
        }
    }
}