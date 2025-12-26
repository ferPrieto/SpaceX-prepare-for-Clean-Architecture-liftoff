package prieto.fernando.feature.launches.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prieto.fernando.feature.launches.domain.mapper.LaunchesDomainFilter
import prieto.fernando.feature.launches.domain.model.LaunchDomainModel
import prieto.fernando.feature.launches.domain.repository.LaunchesRepository
import javax.inject.Inject

interface GetLaunches {
    suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>>
}

class GetLaunchesImpl @Inject constructor(
    private val launchesRepository: LaunchesRepository,
    private val launchesDomainFilter: LaunchesDomainFilter
) : GetLaunches {
    override suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>> =
        launchesRepository.getAllLaunches().map { allLaunchesDomainModel ->
            launchesDomainFilter.filter(
                allLaunchesDomainModel,
                yearFilterCriteria,
                ascendantOrder
            )
        }
}

