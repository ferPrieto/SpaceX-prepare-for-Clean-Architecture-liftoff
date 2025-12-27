package ferprieto.feature.launches.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ferprieto.core.network.ApiService
import ferprieto.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapper
import ferprieto.feature.launches.data.mapper.LaunchesResponseToRepositoryMapper
import ferprieto.feature.launches.domain.model.LaunchDomainModel
import ferprieto.feature.launches.domain.repository.LaunchesRepository
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

