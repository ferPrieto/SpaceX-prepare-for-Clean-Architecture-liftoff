package prieto.fernando.data_api.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    private val companyInfoChannel = ConflatedBroadcastChannel<CompanyInfoRepositoryModel>()
    private val launchesChannel = ConflatedBroadcastChannel<List<LaunchRepositoryModel>>()

    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel> {
        val companyInfoResponse = apiService.getCompanyInfo()
        val companyInfoRepositoryModel =
            companyInfoRepositoryMapper.toRepositoryModel(companyInfoResponse)
        companyInfoChannel.offer(companyInfoRepositoryModel)
        return companyInfoChannel.asFlow()

    }

    override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>> {
        val allLaunchesResponse = apiService.getAllLaunches()
        val launchesRepositoryModel =
            launchesRepositoryMapper.toRepositoryModel(allLaunchesResponse)
        launchesChannel.offer(launchesRepositoryModel)
        return launchesChannel.asFlow()
    }
}
