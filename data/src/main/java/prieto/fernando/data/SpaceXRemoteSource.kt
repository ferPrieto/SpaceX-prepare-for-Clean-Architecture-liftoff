package prieto.fernando.data

import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel

interface SpaceXRemoteSource {
    suspend fun getCompanyInfo(): CompanyInfoRepositoryModel
    suspend fun getAllLaunches(): List<LaunchRepositoryModel>
}