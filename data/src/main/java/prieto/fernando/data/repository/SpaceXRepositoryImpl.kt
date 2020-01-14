package prieto.fernando.data.repository

import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val spaceXRemoteSource: SpaceXRemoteSource,
    private val companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper,
    private val launchesDomainMapper: LaunchesRepositoryToDomainModelMapper
) : SpaceXRepository {
    override suspend fun getCompanyInfo(): CompanyInfoDomainModel {
        val companyInfoRepositoryModel = spaceXRemoteSource.getCompanyInfo()
        return companyInfoDomainMapper.toDomainModel(companyInfoRepositoryModel)
    }

    override suspend fun getAllLaunches(): List<LaunchDomainModel> {
        val allLaunchesRepositoryModel = spaceXRemoteSource.getAllLaunches()
        return launchesDomainMapper.toDomainModel(allLaunchesRepositoryModel)
    }
}
