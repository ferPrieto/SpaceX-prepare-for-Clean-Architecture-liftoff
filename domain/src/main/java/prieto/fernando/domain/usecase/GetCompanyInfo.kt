package prieto.fernando.domain.usecase

import kotlinx.coroutines.flow.Flow
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import javax.inject.Inject

interface GetCompanyInfo {
    suspend fun execute(): Flow<CompanyInfoDomainModel>
}

class GetCompanyInfoImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository
) : GetCompanyInfo {
    override suspend fun execute(): Flow<CompanyInfoDomainModel> = spaceXRepository.getCompanyInfo()
}
