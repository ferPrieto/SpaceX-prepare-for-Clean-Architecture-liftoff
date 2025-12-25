package prieto.fernando.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.feature.launches.domain.model.LaunchDomainModel
import prieto.fernando.feature.launches.domain.repository.LaunchesRepository
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val spaceXRemoteSource: SpaceXRemoteSource,
    private val launchesDomainMapper: LaunchesRepositoryToDomainModelMapper
) : LaunchesRepository {
    override suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>> =
        spaceXRemoteSource.getAllLaunches()
            .map { allLaunchesRepositoryModel ->
                launchesDomainMapper.toDomainModel(allLaunchesRepositoryModel)
            }
}

