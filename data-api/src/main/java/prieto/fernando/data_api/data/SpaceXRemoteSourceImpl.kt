package prieto.fernando.data_api.data

import io.reactivex.Single
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import prieto.fernando.data_repository.SpaceXRemoteSource
import prieto.fernando.data_repository.model.CompanyInfoRepositoryModel
import prieto.fernando.data_repository.model.LaunchesRepositoryModel
import javax.inject.Inject
import javax.inject.Singleton

class SpaceXRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    override fun getCompanyInfo(): Single<CompanyInfoRepositoryModel> =
        apiService.getCompanyInfo().map(companyInfoRepositoryMapper::toRepositoryModel)

    override fun getAllLaunches(): Single<LaunchesRepositoryModel> =
        apiService.getAllLaunches().map(launchesRepositoryMapper::toRepositoryModel)
}
