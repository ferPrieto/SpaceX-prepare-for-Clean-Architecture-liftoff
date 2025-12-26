package prieto.fernando.feature.launches.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import prieto.fernando.data_api.ApiService
import prieto.fernando.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.feature.launches.data.mapper.LaunchesResponseToRepositoryMapper
import prieto.fernando.feature.launches.domain.model.LaunchDomainModel
import prieto.fernando.feature.launches.domain.repository.LaunchesRepository
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToRepositoryMapper: LaunchesResponseToRepositoryMapper,
    private val repositoryToDomainMapper: LaunchesRepositoryToDomainModelMapper
) : LaunchesRepository {
    override suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>> =
        flow {
            val responses = apiService.getAllLaunches()
            val repositoryModels = responseToRepositoryMapper.toRepositoryModel(responses)
            emit(repositoryModels)
        }.map { repositoryModels ->
            repositoryToDomainMapper.toDomainModel(repositoryModels)
        }
}

