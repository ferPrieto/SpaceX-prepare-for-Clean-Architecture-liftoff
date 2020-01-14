package prieto.fernando.domain

import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel

interface SpaceXRepository {
    suspend fun getCompanyInfo(): CompanyInfoDomainModel
    suspend fun getAllLaunches(): List<LaunchDomainModel>
}