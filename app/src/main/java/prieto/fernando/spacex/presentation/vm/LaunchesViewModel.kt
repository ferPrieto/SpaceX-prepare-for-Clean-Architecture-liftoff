package prieto.fernando.spacex.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.core.event.Event
import prieto.fernando.core.event.eventOf
import prieto.fernando.core.presentation.BaseViewModel
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.launches.LaunchesContract
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import timber.log.Timber
import javax.inject.Inject

abstract class LaunchesViewModel : BaseViewModel
<LaunchesContract.Event, LaunchesContract.State, LaunchesContract.Effect>() {
    abstract fun launches(yearFilterCriteria: Int = -1, ascendantOrder: Boolean = false)
    abstract fun openLink(link: String)
    abstract fun onFilterClicked()

    abstract val openLink: LiveData<Event<String>>
    abstract val showDialog: LiveData<Event<Unit>>
}

@HiltViewModel
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
        launches()
    }

    override fun setInitialState(): LaunchesContract.State =
        LaunchesContract.State(
            launches = emptyList(),
            isLoading = true,
            isError = false
        )

    override fun handleEvents(event: LaunchesContract.Event) {
        when (event) {
            is LaunchesContract.Event.Links -> {
                setEffect { LaunchesContract.Effect.FilterClicked }
            }
        }
    }

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        viewModelScope.launch(errorHandler) {
            try {
                getLaunches.execute(yearFilterCriteria, ascendantOrder)
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { launchesDomainModel ->
                        launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)
                            .let { launches ->
                                setState {
                                    copy(
                                        launches = launches,
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