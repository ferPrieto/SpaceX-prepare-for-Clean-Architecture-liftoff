package prieto.fernando.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.LaunchesDomainFilter
import prieto.fernando.domain.model.LaunchDomainModel
import javax.inject.Inject

interface GetLaunches {
    suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Result<List<LaunchDomainModel>>
}

class GetLaunchesImpl(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainFilter: LaunchesDomainFilter,
    private val ioDispatcher: CoroutineDispatcher
) : GetLaunches {
    @Inject
    constructor(
        spaceXRepository: SpaceXRepository,
        launchesDomainFilter: LaunchesDomainFilter
    ) : this(spaceXRepository, launchesDomainFilter, Dispatchers.IO)

    override suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Result<List<LaunchDomainModel>> =
        withContext(ioDispatcher) {
            try {
                val allLaunchesDomainModel = spaceXRepository.getAllLaunches()
                val launches = launchesDomainFilter.filter(
                    allLaunchesDomainModel,
                    yearFilterCriteria,
                    ascendantOrder
                )
                Result.success(launches)
            } catch (throwable: Throwable) {
                Result.failure<List<LaunchDomainModel>>(
                    Exception(
                        "Exception retrieving launches",
                        throwable
                    )
                )
            }
        }
}
