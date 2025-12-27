package ferprieto.feature.dashboard.domain.usecase

import kotlinx.coroutines.flow.Flow
import ferprieto.feature.dashboard.domain.model.CompanyInfoDomainModel
import ferprieto.feature.dashboard.domain.repository.CompanyInfoRepository
import javax.inject.Inject

interface GetCompanyInfo {
    suspend fun execute(): Flow<CompanyInfoDomainModel>
}

class GetCompanyInfoImpl @Inject constructor(
    private val companyInfoRepository: CompanyInfoRepository
) : GetCompanyInfo {
    override suspend fun execute(): Flow<CompanyInfoDomainModel> =
        companyInfoRepository.getCompanyInfo()
}
