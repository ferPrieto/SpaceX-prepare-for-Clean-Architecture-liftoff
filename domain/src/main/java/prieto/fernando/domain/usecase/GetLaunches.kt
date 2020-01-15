package prieto.fernando.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import javax.inject.Inject

interface GetLaunches {
    suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>>
}

class GetLaunchesImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainFilter: LaunchesDomainFilter
) : GetLaunches {
    override suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>> =
        spaceXRepository.getAllLaunches().map { allLaunchesDomainModel ->
            launchesDomainFilter.filter(
                allLaunchesDomainModel,
                yearFilterCriteria,
                ascendantOrder
            )
        }
}
