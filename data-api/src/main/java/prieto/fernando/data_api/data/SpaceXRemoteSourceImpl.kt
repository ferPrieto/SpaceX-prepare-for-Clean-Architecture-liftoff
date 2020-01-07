package prieto.fernando.data_api.data

import io.reactivex.Single
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import javax.inject.Inject

class SpaceXRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    override fun getCompanyInfo(): Single<CompanyInfoRepositoryModel> =
        apiService.getCompanyInfo().map(companyInfoRepositoryMapper::toRepositoryModel)

    override fun getAllLaunches(): Single<List<LaunchRepositoryModel>> =
        apiService.getAllLaunches().map(launchesRepositoryMapper::toRepositoryModel)
}
