package prieto.fernando.domain.usecase

import io.reactivex.Single
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.presentation.GetLaunches
import prieto.fernando.presentation.model.LaunchUiModel
import javax.inject.Inject

class GetLaunchesImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper,
    private val launchesDomainFilter: LaunchesDomainFilter
) : GetLaunches {
    override fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Single<List<LaunchUiModel>> = spaceXRepository.getAllLaunches()
        .map { launches ->
            launchesDomainFilter.filter(launches, yearFilterCriteria, ascendantOrder)
        }.map { launchesFiltered ->
            launchesDomainToUiModelMapper.toUiModel(launchesFiltered)
        }
}
