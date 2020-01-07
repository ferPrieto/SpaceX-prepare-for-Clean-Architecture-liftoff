package prieto.fernando.domain.usecase

import io.reactivex.Single
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import javax.inject.Inject

interface GetLaunches {
    fun execute(yearFilterCriteria: Int, ascendantOrder: Boolean): Single<List<LaunchDomainModel>>
}

class GetLaunchesImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainFilter: LaunchesDomainFilter
) : GetLaunches {
    override fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Single<List<LaunchDomainModel>> = spaceXRepository.getAllLaunches()
        .map { launches ->
            launchesDomainFilter.filter(launches, yearFilterCriteria, ascendantOrder)
        }
}
