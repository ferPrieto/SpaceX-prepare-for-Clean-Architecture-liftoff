package prieto.fernando.domain

import kotlinx.coroutines.flow.Flow
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel

interface SpaceXRepository {
    suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel>
    suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>>
}