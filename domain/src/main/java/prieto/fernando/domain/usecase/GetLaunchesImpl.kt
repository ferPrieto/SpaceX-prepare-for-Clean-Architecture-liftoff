package prieto.fernando.domain.usecase

import io.reactivex.Single
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.presentation.GetLaunches
import prieto.fernando.presentation.model.LaunchesUiModel
import javax.inject.Inject

class GetLaunchesImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
) : GetLaunches {
    override fun execute(): Single<LaunchesUiModel> = spaceXRepository.getAllLaunches()
        .map(launchesDomainToUiModelMapper::toUiModel)
}
