package prieto.fernando.spacex.presentation.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.vm.base.BaseViewModel
import prieto.fernando.spacex.presentation.vm.mapper.ClickableLinkProvider
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getLaunches: GetLaunches,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper,
    private val clickableLinkProvider: ClickableLinkProvider
) : BaseViewModel<LaunchesContract.Event, LaunchesContract.State, LaunchesContract.Effect>() {

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
            launchUiModels = emptyList(),
            isLoading = true,
            isError = false
        )

    override fun handleEvents(event: LaunchesContract.Event) {
        when (event) {
            is LaunchesContract.Event.LinkClicked -> setEffect {
                LaunchesContract.Effect.LinkClicked(
                    event.link
                )
            }
            is LaunchesContract.Event.ClickableLinks ->
                setEffect { clickableLinkProvider.getClickableLink(event.linksUiModel) }

            is LaunchesContract.Event.Filter -> {
                val filteredYear = if (event.year.isNotBlank()) event.year.toInt() else 0
                launches(filteredYear, event.orderedChecked)
            }
        }
    }

    fun launches(yearFilterCriteria: Int = 0, ascendantOrder: Boolean = false) {
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
                                        launchUiModels = launches,
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
