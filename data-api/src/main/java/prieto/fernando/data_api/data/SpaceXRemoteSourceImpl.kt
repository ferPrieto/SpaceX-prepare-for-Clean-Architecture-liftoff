package prieto.fernando.data_api.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.joda.time.DateTime
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel
import prieto.fernando.data.model.LinksRepositoryModel
import prieto.fernando.data.model.RocketRepositoryModel
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    private val _companyInfoSharedFlow = MutableStateFlow(getInitialStateCompanyInfoModel())
    private val companyInfoSharedFlow = _companyInfoSharedFlow.asSharedFlow()
    private val _launchesSharedFlow = MutableStateFlow(getInitialStateLaunchModels())
    private val launchesSharedFlow = _launchesSharedFlow.asSharedFlow()

    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel> {
        companyInfoRepositoryMapper.toRepositoryModel(apiService.getCompanyInfo())
            .let { companyInfoRepositoryModel ->
                _companyInfoSharedFlow.emit(companyInfoRepositoryModel)
            }
        return companyInfoSharedFlow.distinctUntilChanged()

    }

    override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>> {
        launchesRepositoryMapper.toRepositoryModel(apiService.getAllLaunches())
            .let { launchesRepositoryModel ->
                _launchesSharedFlow.emit(launchesRepositoryModel)
            }
        return launchesSharedFlow.distinctUntilChanged()
    }

    private fun getInitialStateCompanyInfoModel() = CompanyInfoRepositoryModel(
        "", "", "", "", -1, -1
    )

    private fun getInitialStateLaunchModels() = listOf(
        LaunchRepositoryModel(
            "", DateTime(), RocketRepositoryModel("", ""), LinksRepositoryModel("", "", ""), false
        )
    )
}
