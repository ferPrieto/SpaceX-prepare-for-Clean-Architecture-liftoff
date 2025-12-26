package prieto.fernando.feature.dashboard.data.mapper

import prieto.fernando.feature.dashboard.data.model.CompanyInfoRepositoryModel
import prieto.fernando.feature.dashboard.domain.model.CompanyInfoDomainModel
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

