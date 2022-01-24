package prieto.fernando.data

import kotlinx.coroutines.flow.Flow
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel

interface SpaceXRemoteSource {
    suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel>
    suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>>
}
