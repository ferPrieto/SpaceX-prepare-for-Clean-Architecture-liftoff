package prieto.fernando.feature.dashboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import prieto.fernando.data_api.ApiService
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapper
import prieto.fernando.feature.dashboard.domain.model.CompanyInfoDomainModel
import prieto.fernando.feature.dashboard.domain.repository.CompanyInfoRepository
import javax.inject.Inject

class CompanyInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToRepositoryMapper: CompanyInfoResponseToRepositoryMapper,
    private val repositoryToDomainMapper: CompanyInfoRepositoryToDomainModelMapper
) : CompanyInfoRepository {
    override suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel> = supervisorScope {
        flow {
            val response = apiService.getCompanyInfo()
            val repositoryModel = responseToRepositoryMapper.toRepositoryModel(response)
            emit(repositoryModel)
        }.map { repositoryModel ->
            repositoryToDomainMapper.toDomainModel(repositoryModel)
        }
    }
}

