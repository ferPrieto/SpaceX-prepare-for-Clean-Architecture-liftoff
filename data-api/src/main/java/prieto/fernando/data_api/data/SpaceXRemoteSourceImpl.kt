package prieto.fernando.data_api.data

import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import javax.inject.Inject

class SpaceXRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    override suspend fun getCompanyInfo(): CompanyInfoRepositoryModel {
        val companyInfoResponse = apiService.getCompanyInfo()
        return companyInfoRepositoryMapper.toRepositoryModel(companyInfoResponse)
    }

    override suspend fun getAllLaunches(): List<LaunchRepositoryModel> {
        val allLaunchesResponse = apiService.getAllLaunches()
        return launchesRepositoryMapper.toRepositoryModel(allLaunchesResponse)
    }
}
