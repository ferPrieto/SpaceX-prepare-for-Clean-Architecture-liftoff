package prieto.fernando.spacex.presentation.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import prieto.fernando.core.presentation.BaseViewModel
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.screens.launches.LaunchesContract
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getLaunches: GetLaunches,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
) : BaseViewModel
<LaunchesContract.Event, LaunchesContract.State, LaunchesContract.Effect>() {

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
        launches(0, false)
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
                setEffect { getClickableLink(event.linksUiModel) }

            is LaunchesContract.Event.Filter -> {
                val filteredYear = if (event.year.isNotBlank()) event.year.toInt() else 0
                launches(filteredYear, event.orderedChecked)
            }
        }
    }

    fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
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

    private fun getClickableLink(linksUiModel: LinksUiModel): LaunchesContract.Effect.ClickableLink =
        when {
            linksUiModel.wikipedia.isNotBlank() && linksUiModel.videoLink.isNotBlank() -> {
                LaunchesContract.Effect.ClickableLink.All(linksUiModel.videoLink, linksUiModel.wikipedia)
            }
            linksUiModel.wikipedia.isNotBlank() && linksUiModel.videoLink.isBlank() -> {
                LaunchesContract.Effect.ClickableLink.Wikipedia(linksUiModel.wikipedia)
            }
            linksUiModel.wikipedia.isBlank() && linksUiModel.videoLink.isNotBlank() -> {
                LaunchesContract.Effect.ClickableLink.Youtube(linksUiModel.videoLink)
            }
            else -> {
                LaunchesContract.Effect.ClickableLink.None
            }
        }
}
