package prieto.fernando.data.mapper

import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.domain.model.CompanyInfoDomainModel
import javax.inject.Inject

interface CompanyInfoRepositoryToDomainModelMapper {
    fun toDomainModel(companyInfoRepositoryModel: CompanyInfoRepositoryModel): CompanyInfoDomainModel
}

class CompanyInfoRepositoryToDomainModelMapperImpl @Inject constructor() :
    CompanyInfoRepositoryToDomainModelMapper {
    override fun toDomainModel(companyInfoRepositoryModel: CompanyInfoRepositoryModel) =
        CompanyInfoDomainModel(
            name = companyInfoRepositoryModel.name,
            founder = companyInfoRepositoryModel.founder,
            founded = companyInfoRepositoryModel.founded,
            employees = companyInfoRepositoryModel.employees,
            launchSites = companyInfoRepositoryModel.launchSites,
            valuation = companyInfoRepositoryModel.valuation
        )
}
