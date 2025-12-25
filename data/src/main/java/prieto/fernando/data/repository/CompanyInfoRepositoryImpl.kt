package prieto.fernando.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.feature.dashboard.domain.model.CompanyInfoDomainModel
import prieto.fernando.feature.dashboard.domain.repository.CompanyInfoRepository
import javax.inject.Inject

class CompanyInfoRepositoryImpl @Inject constructor(
    private val spaceXRemoteSource: SpaceXRemoteSource,
    private val companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper
) : CompanyInfoRepository {
    override suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel> = supervisorScope {
        spaceXRemoteSource.getCompanyInfo()
            .map { companyInfoRepositoryModel ->
                companyInfoDomainMapper.toDomainModel(companyInfoRepositoryModel)
            }
    }
}

