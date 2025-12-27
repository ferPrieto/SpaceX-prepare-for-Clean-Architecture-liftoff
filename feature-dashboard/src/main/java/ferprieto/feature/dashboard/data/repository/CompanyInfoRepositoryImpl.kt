package ferprieto.feature.dashboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import ferprieto.core.network.ApiService
import ferprieto.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import ferprieto.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapper
import ferprieto.feature.dashboard.domain.model.CompanyInfoDomainModel
import ferprieto.feature.dashboard.domain.repository.CompanyInfoRepository
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

