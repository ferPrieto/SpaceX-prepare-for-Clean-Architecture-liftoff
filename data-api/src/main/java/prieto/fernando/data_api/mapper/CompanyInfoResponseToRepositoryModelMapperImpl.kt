package prieto.fernando.data_api.mapper

import dagger.Reusable
import prieto.fernando.data_api.model.CompanyInfoResponse
import prieto.fernando.data_repository.model.CompanyInfoRepositoryModel
import javax.inject.Inject

interface CompanyInfoResponseToRepositoryModelMapper {
    fun toRepositoryModel(companyInfoResponse: CompanyInfoResponse): CompanyInfoRepositoryModel
}

@Reusable
class CompanyInfoResponseToRepositoryModelMapperImpl @Inject constructor() :
    CompanyInfoResponseToRepositoryModelMapper {
    override fun toRepositoryModel(companyInfoResponse: CompanyInfoResponse): CompanyInfoRepositoryModel =
        CompanyInfoRepositoryModel(
            name = companyInfoResponse.name,
            founder = companyInfoResponse.founder,
            founded = companyInfoResponse.founded,
            employees = companyInfoResponse.employees,
            launchSites = companyInfoResponse.launchSites,
            valuation = companyInfoResponse.valuation
        )
}
