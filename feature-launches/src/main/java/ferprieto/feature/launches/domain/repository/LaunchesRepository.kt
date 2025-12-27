package ferprieto.feature.launches.domain.repository

import kotlinx.coroutines.flow.Flow
import ferprieto.feature.launches.domain.model.LaunchDomainModel

/**
 * Repository interface for launches information.
 * This is owned by the Launches feature and defines what data it needs.
 * The implementation lives in the core-data module.
 */
interface LaunchesRepository {
    suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>>
}

