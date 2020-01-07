package prieto.fernando.domain.usecase

import io.reactivex.Single
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import javax.inject.Inject

interface GetCompanyInfo {
    fun execute(): Single<CompanyInfoDomainModel>
}

class GetCompanyInfoImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository
) : GetCompanyInfo {
    override fun execute(): Single<CompanyInfoDomainModel> = spaceXRepository.getCompanyInfo()
}
